package com.example.demo.repository;

import com.example.demo.DTO.FilterDTO;
import com.example.demo.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

@RequiredArgsConstructor
public class PlayerSpecification implements Specification<Player> {
    private final FilterDTO filterDTO;

    @Override
    public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (filterDTO.getName() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + filterDTO.getName() + "%"));
        }

        if (filterDTO.getTitle() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("title"), "%" + filterDTO.getTitle() + "%"));
        }

        if (filterDTO.getBanned() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("banned"), filterDTO.getBanned()));
        }

        if (filterDTO.getRace() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("race"), filterDTO.getRace().name()));
        }

        if (filterDTO.getProfession() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("profession"), filterDTO.getProfession().name()));
        }

        if (filterDTO.getAfter() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), new Date(filterDTO.getAfter())));
        }

        if (filterDTO.getBefore() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), new Date(filterDTO.getBefore())));
        }

        if (filterDTO.getMinExperience() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), filterDTO.getMinExperience()));
        }

        if (filterDTO.getMaxExperience() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("experience"), filterDTO.getMaxExperience()));
        }

        if (filterDTO.getMinLevel() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("level"), filterDTO.getMinLevel()));
        }

        if (filterDTO.getMaxLevel() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("level"), filterDTO.getMaxLevel()));
        }

        return predicate;
    }
}
