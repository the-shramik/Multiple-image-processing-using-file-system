package com.test.service;

import com.test.model.ImageDetails;
import com.test.model.Product;
import com.test.model.dto.ProductDto;
import com.test.repository.IImageDetailsRepository;
import com.test.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final IProductRepository productRepository;

    private final IImageDetailsRepository iImageDetailsRepository;

    @Value("${image.upload.dir}")
    String imageUploadDir;

    public Product addProduct(Product product, List<MultipartFile> productImages){

        Product savedProduct = productRepository.save(product);
        List<ImageDetails> imageDetails=new ArrayList<>();
        productImages.forEach(productImage->{
            ImageDetails imageDetail=new ImageDetails();

            String uniqueFileName = UUID.randomUUID().toString() + "_" + productImage.getOriginalFilename();
            String filePath = Paths.get(imageUploadDir, uniqueFileName).toString();


            try {

                Files.createDirectories(Paths.get(imageUploadDir));
                productImage.transferTo(new File(filePath));

                imageDetail.setImageName(uniqueFileName);
                imageDetail.setProduct(savedProduct);
                iImageDetailsRepository.save(imageDetail);
                imageDetails.add(imageDetail);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        return savedProduct;
    }


    public List<ProductDto> getProducts(){

        List<ProductDto> products=new ArrayList<>();
        productRepository.findAll().forEach(product -> {

             List<String> imageBase64s=new ArrayList<>();

             ProductDto productDto=new ProductDto();

             productDto.setProductId(product.getProductId());
             productDto.setProductName(product.getProductName());
             productDto.setProductPrice(product.getProductPrice());

             product.getImageDetails().forEach(imageDetail->{
                 String imageName=imageDetail.getImageName();
                 String path_dir = imageUploadDir + File.separator + imageName;

                 try (FileInputStream imageStream = new FileInputStream(path_dir)) {
                     byte[] imageBytes = IOUtils.toByteArray(imageStream);
                     String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
                     imageBase64s.add(imageBase64);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             });

             productDto.setImageBase64(imageBase64s);
             products.add(productDto);
        });

        return products;
    }
}
