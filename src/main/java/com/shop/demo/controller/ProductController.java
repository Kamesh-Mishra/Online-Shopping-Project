package com.shop.demo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.shop.demo.dto.ProductDto;
import com.shop.demo.exceptions.AlreadyExistsException;
import com.shop.demo.exceptions.ResourceNotFoundException;
import com.shop.demo.model.Product;
import com.shop.demo.request.AddProductRequest;
import com.shop.demo.request.ProductUpdateRequest;
import com.shop.demo.response.ApiResponse;
import com.shop.demo.service.product.IProductService;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

	private final IProductService productService;

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
		return  ResponseEntity.ok(new ApiResponse("success", convertedProducts));
	}

	@GetMapping("/product/{productId}/product")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
		try {
			Product product = productService.getProductById(productId);
			ProductDto productDto = productService.convertToDto(product);
			return  ResponseEntity.ok(new ApiResponse("success", productDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
		try {
			Product theProduct = productService.addProduct(product);
			ProductDto productDto = productService.convertToDto(theProduct);
			return ResponseEntity.ok(new ApiResponse("Add product success!", productDto));
		} catch (AlreadyExistsException e) {
			return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/product/{productId}/update")
	public  ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId) {
		try {
			Product theProduct = productService.updateProduct(request, productId);
			ProductDto productDto = productService.convertToDto(theProduct);
			return ResponseEntity.ok(new ApiResponse("Update product success!", productDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/product/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
		try {
			productService.deleteProductById(productId);
			return ResponseEntity.ok(new ApiResponse("Delete product success!", productId));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/products/by/brand-and-name")
	public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
		try {
			List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
			if (products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return  ResponseEntity.ok(new ApiResponse("success", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/products/by/category-and-brand")
	public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
		try {
			List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
			if (products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return  ResponseEntity.ok(new ApiResponse("success", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
		}
	}

	@GetMapping("/products/{name}/products")
	public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
		try {
			List<Product> products = productService.getProductsByName(name);
			if (products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return  ResponseEntity.ok(new ApiResponse("success", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
		}
	}

	@GetMapping("/product/by-brand")
	public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand) {
		try {
			List<Product> products = productService.getProductsByBrand(brand);
			if (products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return  ResponseEntity.ok(new ApiResponse("success", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}

	@GetMapping("/product/{category}/all/products")
	public ResponseEntity<ApiResponse> findProductsByCategory(@PathVariable String category) {
		try {
			List<Product> products = productService.getProductsByCategory(category);
			if (products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return  ResponseEntity.ok(new ApiResponse("success", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}

	// New end point 1
	@GetMapping("/distinct/products")
	public ResponseEntity<ApiResponse> getDistinctProductsByCategory() {
		try {
			List<Product> products = productService.findDistinctProductsByName();
			if (products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return  ResponseEntity.ok(new ApiResponse("success", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}

	// New end point 2
	@GetMapping("/distinct/brands")
	public ResponseEntity<ApiResponse> getAllDistinctBrands() {
		try {
			List<String> brands = productService.getAllDistinctBrands();
			return  ResponseEntity.ok(new ApiResponse("success", brands));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}


	@GetMapping("/product/count/by-brand/and-name")
	public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
		try {
			var productCount = productService.countProductsByBrandAndName(brand, name);
			return ResponseEntity.ok(new ApiResponse("Product count!", productCount));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
		}
	}


}