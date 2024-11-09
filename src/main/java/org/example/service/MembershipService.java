package org.example.service;

import org.example.dao.MembershipDAO;
import org.example.model.Membership;

public class MembershipService {
    private final MembershipDAO membershipDAO = new MembershipDAO();

    // Retrieve membership details by ID
    public Membership getMembershipDetails(Long membershipId) {
        return membershipDAO.findMembershipById(membershipId);
    }

    // Update membership details
    public void updateMembership(Membership membership) {
        membershipDAO.saveOrUpdateMembership(membership);
    }
}
