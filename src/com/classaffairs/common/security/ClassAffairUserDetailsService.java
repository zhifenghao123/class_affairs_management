package com.classaffairs.common.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.classaffairs.entity.Administrator;
import com.classaffairs.entity.Authority;
import com.classaffairs.entity.Role;
import com.classaffairs.entity.Student;
import com.classaffairs.service.AdministratorService;
import com.classaffairs.service.AuthorityRetrieveService;
import com.classaffairs.service.RoleService;
import com.classaffairs.service.StudentService;
@Service(value = "classAffairUserDetailsService")
public class ClassAffairUserDetailsService implements UserDetailsService {
	@Autowired 
	private AdministratorService itsAdministratorService;
	/*@Autowired
	private AuthorityService itsAuthorityService;*/
	@Autowired
	@Qualifier(value = "authorityRetrieveServiceImpl")
	private AuthorityRetrieveService itsAuthorityService;
	@Autowired
	private RoleService itsRoleService;
	
	@Autowired
	private StudentService itsStudentService;
	
	@Override
	public UserDetails loadUserByUsername(String userNo)
			throws UsernameNotFoundException, DataAccessException {
		ClassAffairUserDetails userDetail = new ClassAffairUserDetails();
		
		Set<GrantedAuthority> AUTHORITIES = new HashSet<GrantedAuthority>();
		AUTHORITIES.add(new ClassAffairGrantedAuthority("ROLE_ALL"));
		
		if(userNo.contains("-")){//系统管理员
			String administratorNoReal = userNo.substring(0, userNo.length()-1);
			Administrator administrator = itsAdministratorService.findAdministratorByAdministratorNo(administratorNoReal);
			if(administrator != null){
				userDetail.setUsername(userNo);
				userDetail.setPassword(administrator.getPassword());
				Long administratorRoleId = administrator.getRoleId();
				if(administratorRoleId != null && (!administratorRoleId.equals(""))){
					Role role = itsRoleService.findRoleByRoleId(administratorRoleId);
					if(role != null && role.getState() == Role.ONUSE){
						String authorityIds = role.getAuthorityCode();
						if(authorityIds != null && !("").equals(authorityIds)){
							String[] ids = authorityIds.split(",");
							for(String id: ids){
								Authority authority = itsAuthorityService.findAuthorityByAuthorityId(Long.valueOf(id));
								if(authority != null && authority.getState() == Authority.ONUSE){
									AUTHORITIES.add(new ClassAffairGrantedAuthority(authority.getAuthorityId().toString()));
								}
							}
						}
					}
				}
				userDetail.setAuthorities(AUTHORITIES);
				userDetail.setAccountNonExpired(true);
				userDetail.setAccountNonLocked(true);
				userDetail.setCredentialsNonExpired(true);
				userDetail.setEnabled(true);
			}else{
				throw new UsernameNotFoundException("Administrator Not Found");
			}
		}else{//普通学生用户
			Student student = itsStudentService.findStudentByStudentNo(userNo);
			if(student != null){
				userDetail.setUsername(userNo);
				userDetail.setPassword(student.getPassword());

				if (student.getState() == student.STATE_NORMAL) {
					Authority authority = itsAuthorityService.findAuthorityByAuthorityId(1l);//普通学生
					if (authority != null && Authority.ONUSE == authority.getState()) {
						AUTHORITIES.add(new ClassAffairGrantedAuthority(String.valueOf(authority.getAuthorityId())));
						
					}
				}
				userDetail.setAuthorities(AUTHORITIES);
				userDetail.setAccountNonExpired(true);
				userDetail.setAccountNonLocked(true);
				userDetail.setCredentialsNonExpired(true);
				userDetail.setEnabled(true);
			}else{
				throw new UsernameNotFoundException("Student Not Found");
			}
			
		}
		return userDetail;
	}

}
