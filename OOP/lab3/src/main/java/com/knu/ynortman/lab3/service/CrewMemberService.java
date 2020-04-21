package com.knu.ynortman.lab3.service;

import java.util.Optional;

import com.knu.ynortman.lab3.model.CrewMember;


public interface CrewMemberService {
	public CrewMember createMember(CrewMember crewMember);
	public Iterable<CrewMember> getAllMembers();
	public Optional<CrewMember> getMember(int id);
	public CrewMember update(CrewMember member);
	public void delete(CrewMember flight);
	public void deleteById(int id);
}
