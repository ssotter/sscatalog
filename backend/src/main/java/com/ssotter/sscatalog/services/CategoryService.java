package com.ssotter.sscatalog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssotter.sscatalog.dto.CategoryDTO;
import com.ssotter.sscatalog.entities.Category;
import com.ssotter.sscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list =  repository.findAll();
		
		List<CategoryDTO> listDTO = new ArrayList<>();
		for (Category cat : list) {
			listDTO.add(new CategoryDTO(cat));
		}
		
		return listDTO;
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.get();
		return new CategoryDTO(entity);
	}
}
