package com.example.StudyDemo.service;

import com.example.StudyDemo.entity.Employee;
import com.example.StudyDemo.exception.EmployeeNotFoundException;
import com.example.StudyDemo.repository.EmployeeRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class EmployeeServiceMockTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService service;

    // ✅ 成功案例
    @Test
    void testFindById_success() {
        Employee emp = new Employee();
        emp.setName("Sam");
        // 模擬 repository.findById(1L) 回傳 emp
        when(repository.findById(1L))
                .thenReturn(Optional.of(emp));

        Employee result = service.findById(1L);
        // 驗證結果
        assertNotNull(result);
        // 驗證員工名稱
        assertEquals("Sam", result.getName(), "員工名稱應該是 Sam");
        //repository.findById(1L) が呼び出されたことを確認する
         verify(repository).findById(1L);
    }

    // ❌ 找不到資料（重點🔥）
    @Test
    void testFindById_notFound() {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        
        //when(repository.findById(1L))
         //       .thenReturn(Optional.of(new Employee())); // 假裝有資料


        assertThrows(EmployeeNotFoundException.class, () -> {
            service.findById(1L);
        });
    }
}