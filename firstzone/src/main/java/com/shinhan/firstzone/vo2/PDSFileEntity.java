package com.shinhan.firstzone.vo2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//하나의 Board에 첨부파일이 여러개 있다.
@Table(name = "t_pdsfile")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PDSFileEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Long fno;
	String pdsfilename;
}