package com.teamnetsol.dhp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.teamnetsol.dhp.entity.Item;
import com.teamnetsol.dhp.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> getItems(){
		return itemRepository.findAll(new PageRequest(0, 20, Direction.DESC, "publishedDate")).getContent();	
	}
}
