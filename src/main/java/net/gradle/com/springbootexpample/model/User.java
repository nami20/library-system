package net.gradle.com.springbootexpample.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", length = 100, nullable = false)
	private String username;

	@Column(name = "email", length = 100, nullable = false)
	private String email;

	@Column(name = "password", length = 100, nullable = false)
	private String password;

	@Column(name = "mobile", length = 100, nullable = false)
	private String mobile;

	@Column(name = "status", length = 100, nullable = false)
	private UserStatus status;

	@Column(name = "role", length = 100, nullable = false)
	private UserRole userRole;
	
	@Transient
    private String passwordConfirm;

	@Column
	@CreationTimestamp
	private LocalDateTime createdOn;

}
