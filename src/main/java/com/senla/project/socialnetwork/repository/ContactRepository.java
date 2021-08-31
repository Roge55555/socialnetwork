package com.senla.project.socialnetwork.repository;

import com.senla.project.socialnetwork.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {

//    List<Contact> findByCreatorAndContactLevelOrMateAndContactLevel();
//
//    List<Contact> findByCreatorAndCreatorRoleOrMateAndMateRole();
//
//    List<Contact> findByCreatorAndCreatorRoleAndMateAndMateRoleAndDateConnectedBetweenOrCreatorAndCreatorRoleAndMateAndMateRoleAndDateConnectedBetween();

}
