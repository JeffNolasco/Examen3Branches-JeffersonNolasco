package com.banquito.core.branches.service;

import org.junit.jupiter.api.Test;
import com.banquito.core.branches.exception.CRUDException;
import com.banquito.core.branches.model.Branch;
import com.banquito.core.branches.repository.BranchRepository;
import com.banquito.core.branches.service.BranchService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    private BranchService branchService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        branchService = new BranchService(branchRepository);
    }

    @Test
    public void testLookByIdExistingBranch() throws CRUDException {
        String branchId = "someId";
        Branch expectedBranch = new Branch();
        expectedBranch.setId(branchId);

        when(branchRepository.findById(branchId)).thenReturn(Optional.of(expectedBranch));

        Branch result = branchService.lookById(branchId);

        assertEquals(expectedBranch, result);
    }

    @Test
    public void testLookByIdNonExistingBranch() throws CRUDException {
        String branchId = "nonExistentId";

        when(branchRepository.findById(branchId)).thenReturn(Optional.empty());

        branchService.lookById(branchId);
    }

    @Test
    public void testLookByCodeExistingBranch() {
        String branchCode = "someCode";
        Branch expectedBranch = new Branch();
        expectedBranch.setCode(branchCode);

        when(branchRepository.findByCode(branchCode)).thenReturn(expectedBranch);

        Branch result = branchService.lookByCode(branchCode);

        assertEquals(expectedBranch, result);
    }

    @Test
    public void testLookByCodeNonExistingBranch() {
        String branchCode = "nonExistentCode";

        when(branchRepository.findByCode(branchCode)).thenReturn(null);

        Branch result = branchService.lookByCode(branchCode);

        assertNull(result);
    }

    @Test
    public void testGetAllBranches() {
        List<Branch> expectedBranches = new ArrayList<>();
        expectedBranches.add(new Branch());
        expectedBranches.add(new Branch());

        when(branchRepository.findAll()).thenReturn(expectedBranches);

        List<Branch> result = branchService.getAll();

        assertEquals(expectedBranches, result);
    }

    @Test
    public void testCreateBranch() throws CRUDException {
        Branch newBranch = new Branch();
        newBranch.setCode("newCode");

        branchService.create(newBranch);

        verify(branchRepository).save(newBranch);
    }

    @Test
    public void testCreateBranchException() throws CRUDException {
        Branch newBranch = new Branch();
        newBranch.setCode("newCode");

        when(branchRepository.save(newBranch)).thenThrow(new RuntimeException("Some error"));

        branchService.create(newBranch);
    }

    @Test
    public void testUpdateExistingBranch() throws CRUDException {
        String branchCode = "existingCode";
        Branch existingBranch = new Branch();
        existingBranch.setCode(branchCode);

        when(branchRepository.findByCode(branchCode)).thenReturn(existingBranch);

        Branch updatedBranch = new Branch();
        updatedBranch.setName("Updated Name");

        branchService.update(branchCode, updatedBranch);

        assertEquals(updatedBranch.getName(), existingBranch.getName());
        verify(branchRepository).save(existingBranch);
    }

    @Test
    public void testUpdateNonExistingBranch() throws CRUDException {
        String branchCode = "nonExistentCode";

        when(branchRepository.findByCode(branchCode)).thenReturn(null);

        branchService.update(branchCode, new Branch());
    }
}