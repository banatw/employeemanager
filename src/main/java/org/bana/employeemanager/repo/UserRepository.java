package org.bana.employeemanager.repo;

import org.bana.employeemanager.model.UserApp;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<UserApp, Long> {
    UserApp findByUsername(String username);
}