package com.example.crud_application.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.crud_application.model.Item;
import com.example.crud_application.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllItems() throws Exception {
        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("Laptop");
        item1.setDescription("Gaming Laptop");
        item1.setPrice(1500.0);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("Phone");
        item2.setDescription("Smartphone");
        item2.setPrice(800.0);

        when(itemService.getAllItems()).thenReturn(Arrays.asList(item1, item2));

        mockMvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[1].name").value("Phone"));

        verify(itemService, times(1)).getAllItems();
    }

    @Test
    void testGetItemById() throws Exception {
        Item item = new Item();
        item.setId(1L);
        item.setName("Laptop");
        item.setDescription("Gaming Laptop");
        item.setPrice(1500.0);

        when(itemService.getItemById(1L)).thenReturn(Optional.of(item));

        mockMvc.perform(get("/api/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));

        verify(itemService, times(1)).getItemById(1L);
    }

    @Test
    void testCreateItem() throws Exception {
        Item item = new Item();
        item.setName("Laptop");
        item.setDescription("Gaming Laptop");
        item.setPrice(1500.0);

        when(itemService.saveItem(any(Item.class))).thenReturn(item);

        mockMvc.perform(post("/api/items")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));

        verify(itemService, times(1)).saveItem(any(Item.class));
    }

    @Test
    void testUpdateItem() throws Exception {
        Item updatedItem = new Item();
        updatedItem.setId(1L);
        updatedItem.setName("Updated Laptop");
        updatedItem.setDescription("Updated Description");
        updatedItem.setPrice(1600.0);

        when(itemService.updateItem(eq(1L), any(Item.class))).thenReturn(updatedItem);

        mockMvc.perform(put("/api/items/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Laptop"));

        verify(itemService, times(1)).updateItem(eq(1L), any(Item.class));
    }

    @Test
    void testDeleteItem() throws Exception {
        doNothing().when(itemService).deleteItem(1L);

        mockMvc.perform(delete("/api/items/1"))
                .andExpect(status().isOk());

        verify(itemService, times(1)).deleteItem(1L);
    }
}
