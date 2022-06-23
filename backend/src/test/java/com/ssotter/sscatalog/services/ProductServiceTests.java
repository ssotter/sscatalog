package com.ssotter.sscatalog.services;


import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ssotter.sscatalog.dto.ProductDTO;
import com.ssotter.sscatalog.entities.Category;
import com.ssotter.sscatalog.entities.Product;
import com.ssotter.sscatalog.repositories.CategoryRepository;
import com.ssotter.sscatalog.repositories.ProductRepository;
import com.ssotter.sscatalog.services.exceptions.DatabaseException;
import com.ssotter.sscatalog.services.exceptions.ResourceNotFoundException;
import com.ssotter.sscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	private long existingId;
	private long notExistsId;
	private long dependentId;
	private PageImpl<Product> page;
	private Product product;
	private Category category;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		notExistsId = 2L;
		dependentId = 3L;
		product = Factory.createProduct();
		category = Factory.createCategory();
		page = new PageImpl<>(List.of(product));
		
		Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(notExistsId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.getOne(existingId)).thenReturn(product);
		Mockito.when(repository.getOne(notExistsId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(categoryRepository.getOne(existingId)).thenReturn(category);
		Mockito.when(categoryRepository.getOne(notExistsId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(notExistsId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

	}
	
	@Test
	public void UpdateShouldThrowResourceNotFoundExceptionWhenIdNotExists() {
		
		ProductDTO productDTO = Factory.createProductDTO();
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			ProductDTO result = service.update(notExistsId, productDTO);
		});
	}
	
	@Test
	public void UpdateShouldReturnProductDtoWhenIdExists() {
		
		ProductDTO productDTO = Factory.createProductDTO();
		
		ProductDTO result = service.update(existingId, productDTO);
		
		Assertions.assertNotNull(result);
		
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdNotExists() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			ProductDTO result = service.findById(notExistsId);
		});
		
	}
	
	@Test
	public void findByIdShouldReturnProductDtoWhenIdExists() {
		
		ProductDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		
	}
	
	@Test
	public void findAllPagedShouldReturnPage() {
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = service.findAllPaged(pageable);
		
		Assertions.assertNotNull(result);
		Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
		
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {

		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
	}
	
	@Test
	public void deleteShouldThrowEmptyResourceNotFoundExceptionWhenIdNotExists() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(notExistsId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(notExistsId);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	}
	
	
	
	

}
