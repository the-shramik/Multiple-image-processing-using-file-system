package com.test.repository;

import com.test.model.ImageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageDetailsRepository extends JpaRepository<ImageDetails,Long> {
}
