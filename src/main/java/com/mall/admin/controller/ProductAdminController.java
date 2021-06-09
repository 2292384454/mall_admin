package com.mall.admin.controller;

import com.mall.admin.pojo.AdminInfo;
import com.mall.admin.pojo.OtherConfig;
import com.mall.admin.pojo.ProductInfo;
import com.mall.admin.pojo.Type;
import com.mall.admin.service.ProductInfoService;
import com.mall.admin.service.TypeService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * 描述.
 * <p>
 * 创建时间: 2021/6/1 21:28
 *
 * @author KevinHwang
 */
@Controller
@RequestMapping("/admin/product_admin")
@SessionAttributes(value = {"product", "typeList"})
@Log4j2
public class ProductAdminController {
    private final OtherConfig otherConfig;
    private final ProductInfoService productInfoService;
    private final TypeService typeService;

    @Autowired
    public ProductAdminController(OtherConfig otherConfig, ProductInfoService productInfoService, TypeService typeService) {
        this.otherConfig = otherConfig;
        this.productInfoService = productInfoService;
        this.typeService = typeService;
    }

    /**
     * 处理对/product_list的GET请求
     */
    @GetMapping("/product_list")
    public String getProductList(Model model, HttpServletRequest request) {
        //页码
        String pageNumStr = request.getParameter("pageNum");
        int pageNum = 0;
        if (StringUtils.isNotBlank(pageNumStr)) {
            try {
                pageNum = Integer.parseInt(pageNumStr);
            } catch (Exception ignored) {
            }
        }
        //商品编号
        String codeStr = request.getParameter("code");
        //商品名称
        String nameStr = request.getParameter("name");
        //商品种类
        String typeStr = request.getParameter("type");
        //商品品牌
        String brandStr = request.getParameter("brand");
        //商品价格from
        String fromStr = request.getParameter("from");
        //商品价格to
        String toStr = request.getParameter("to");
        //商品状态
        String statusStr = request.getParameter("status");

        //实现条件查询，组合查询
        Specification<ProductInfo> specification = (root, criteriaQuery, criteriaBuilder) -> {
            //用列表装载断言对象
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(codeStr)) {
                //精确查询，equal
                Predicate predicate = criteriaBuilder.equal(root.get("code").as(String.class), codeStr);
                predicates.add(predicate);
            }
            if (StringUtils.isNotBlank(nameStr)) {
                //模糊查询，like
                Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + nameStr + "%");
                predicates.add(predicate);
            }
            if (StringUtils.isNotBlank(typeStr)) {
                try {
                    int typeId = Integer.parseInt(typeStr);
                    //精确查询，equal
                    Predicate predicate = criteriaBuilder.equal(root.get("type").get("id"), typeId);
                    predicates.add(predicate);
                } catch (Exception ignored) {
                }
            }
            if (StringUtils.isNotBlank(brandStr)) {
                //精确查询，equal
                Predicate predicate = criteriaBuilder.equal(root.get("brand").as(String.class), brandStr);
                predicates.add(predicate);
            }
            if (StringUtils.isNotBlank(fromStr)) {
                double from = Double.MAX_VALUE;
                try {
                    from = Double.parseDouble(fromStr);
                } catch (Exception ignored) {
                }
                //大于等于from
                Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price").as(Double.class), from);
                predicates.add(predicate);
            }
            if (StringUtils.isNotBlank(toStr)) {
                double to = Double.MIN_EXPONENT;
                try {
                    to = Double.parseDouble(toStr);
                } catch (Exception ignored) {
                }
                //小于等于to
                Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("price").as(Double.class), to);
                predicates.add(predicate);
            }
            if (StringUtils.isNotBlank(statusStr)) {
                try {
                    int status = Integer.parseInt(statusStr);
                    //精确查询，equal
                    Predicate predicate = criteriaBuilder.equal(root.get("status").as(Integer.class), status);
                    predicates.add(predicate);
                } catch (Exception ignored) {
                }
            }

            //判断是否有断言，如果没有则返回空，不进行条件组合
            if (predicates.size() == 0) {
                return null;
            }
            //转换为数组，组合查询条件
            Predicate[] p = new Predicate[predicates.size()];
            return criteriaBuilder.and(predicates.toArray(p));
        };
        //交给DAO处理查询任务
        Page<ProductInfo> products = productInfoService.findProductInfo(specification, pageNum);
        model.addAttribute("products", products);
        model.addAttribute("pageNum", pageNum);
        List<Type> typeList = typeService.findType();
        model.addAttribute("typeList", typeList);
        return "product_list";
    }


    /**
     * 处理对/product_list的POST请求
     */
    @PostMapping("/product_list")
    public String postProductList(HttpServletRequest request) {
        //获取要下架的商品id
        String[] offIds = request.getParameterValues("offIds");
        if (offIds == null || offIds.length == 0) {
            return "redirect:/admin/product_admin/product_list?error=no_product_selected";
        }
        //获取操作员，日志记录用
        AdminInfo adminInfo = (AdminInfo) request.getSession().getAttribute("adminInfo");
        //逐条下架
        for (String idStr : offIds) {
            Long id = Long.parseLong(idStr);
            productInfoService.modifyStatus(id, 0);
            ProductInfo productInfo = productInfoService.getProductInfoById(id);
            log.info(adminInfo.getUsername() + " 下架了商品 " + productInfo);
        }
        return "redirect:/admin/product_admin/product_list?off_count=" + offIds.length;
    }

    /**
     * 处理对/type_list的GET请求
     */
    @GetMapping("/type_list")
    public String getTypeList(Model model, @RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {
        Page<Type> types = typeService.findType(pageNum);
        model.addAttribute("types", types);
        model.addAttribute("pageNum", pageNum);
        return "type_list";
    }

    /**
     * 处理对/type_list的GET请求
     */
    @PostMapping("/type_list")
    public String postTypeList(@RequestParam(value = "type_name") String typeName) {
        typeService.addType(typeName);
        return "redirect:/admin/product_admin/type_list";
    }


    @GetMapping("/create_pro")
    public String getCreatePro(Model model) {
        model.addAttribute("product", new ProductInfo());
        model.addAttribute("typeList", typeService.findType());

        return "create_pro";
    }

    @PostMapping("/create_pro")
    public String postCreatePro(Model model, @RequestParam("file") MultipartFile file,
                                @Valid @ModelAttribute("product") ProductInfo product, Errors errors,
                                SessionStatus sessionStatus, HttpServletRequest request) throws IOException {
        //存储图片的路径是相对这个路径的
        File staticFolder = new File(otherConfig.getStaticPath());
        //服务器端存放商品图片的文件夹路径，不存在就创建
        File folder = new File(staticFolder, "images/products");
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
        //如果 用户没选择图片 && 之前也没有图片
        boolean noPic = file.isEmpty();
        if (product.getPic() != null && product.getPic().length() > 0) {
            File oldFile = new File(staticFolder, product.getPic());
            noPic = noPic && (!oldFile.exists());
        }
        if (errors.hasErrors() || noPic) {
            if (product.getType() == null) {
                //防止前端因为type==null取不到type.id报错
                product.setType(new Type());
            }
            if (noPic) {
                model.addAttribute("error", "请选择商品图片");
            }
            return "create_pro";
        }
        //从session获取当前操作人员，日志记录用
        AdminInfo adminInfo = (AdminInfo) request.getSession().getAttribute("adminInfo");
        if (!file.isEmpty()) {
            //获取文件后缀名，主要是为了给文件重命名
            String extName = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf("."));
            // 获得当前时间
            String time = String.valueOf(System.currentTimeMillis());
            File targetFile;
            String fileName;
            do {
                // 随机生成文件编号
                String random = String.valueOf(new Random().nextInt(100000));
                fileName = time + "-" + random + extName;
                //实例化一个File对象，表示目标文件（含服务器端路径）
                targetFile = new File(folder, fileName);
            } while (targetFile.exists());
            //将上传文件写到服务器上指定的文件
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(targetFile));
            product.setPic("images/products/" + fileName);
        }
        productInfoService.save(product);
        log.info(adminInfo.getUsername() + " 添加（修改）商品 " + product);

        sessionStatus.setComplete();
        return "redirect:/admin/product_admin/product_list?create=true";
    }

    @GetMapping("/edit_pro")
    public String getEditPro(Model model, HttpServletRequest request) {
        Long editId = Long.parseLong(request.getParameter("edit_id"));
        model.addAttribute("product", productInfoService.getProductInfoById(editId));
        model.addAttribute("typeList", typeService.findType());

        return "create_pro";
    }
}
