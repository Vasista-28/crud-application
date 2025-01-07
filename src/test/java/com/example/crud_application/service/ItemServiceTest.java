package com.example.crud_application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.crud_application.model.Item;
import com.example.crud_application.repository.ItemRepository;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllItems() {
        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("Item 1");
        item1.setDescription("Description 1");
        item1.setPrice(100.0);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("Item 2");
        item2.setDescription("Description 2");
        item2.setPrice(200.0);

        List<Item> mockItems = Arrays.asList(item1, item2);

        when(itemRepository.findAll()).thenReturn(mockItems);

        List<Item> items = itemService.getAllItems();

        assertEquals(2, items.size());
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void testGetItemById() {
        Item mockItem = new Item();
        mockItem.setId(1L);
        mockItem.setName("Item 1");
        mockItem.setDescription("Description 1");
        mockItem.setPrice(100.0);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(mockItem));

        Optional<Item> item = itemService.getItemById(1L);

        assertTrue(item.isPresent());
        assertEquals("Item 1", item.get().getName());
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveItem() {
        Item mockItem = new Item();
        mockItem.setName("Item 1");
        mockItem.setDescription("Description 1");
        mockItem.setPrice(100.0);

        when(itemRepository.save(mockItem)).thenReturn(mockItem);

        Item savedItem = itemService.saveItem(mockItem);

        assertNotNull(savedItem);
        assertEquals("Item 1", savedItem.getName());
        verify(itemRepository, times(1)).save(mockItem);
    }

    @Test
    void testUpdateItem() {
        Item existingItem = new Item();
        existingItem.setId(1L);
        existingItem.setName("Old Item");
        existingItem.setDescription("Old Description");
        existingItem.setPrice(50.0);

        Item updatedDetails = new Item();
        updatedDetails.setName("Updated Item");
        updatedDetails.setDescription("Updated Description");
        updatedDetails.setPrice(150.0);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(existingItem));
        when(itemRepository.save(existingItem)).thenReturn(existingItem);

        Item updatedItem = itemService.updateItem(1L, updatedDetails);

        assertEquals("Updated Item", updatedItem.getName());
        assertEquals("Updated Description", updatedItem.getDescription());
        assertEquals(150.0, updatedItem.getPrice());
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).save(existingItem);
    }

    @Test
    void testDeleteItem() {
        Long itemId = 1L;

        doNothing().when(itemRepository).deleteById(itemId);

        itemService.deleteItem(itemId);

        verify(itemRepository, times(1)).deleteById(itemId);
    }
}
