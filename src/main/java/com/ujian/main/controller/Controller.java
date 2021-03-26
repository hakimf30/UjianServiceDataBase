package com.ujian.main.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.ujian.main.modul.Bonus;
import com.ujian.main.modul.BonusRowMapper;
import com.ujian.main.modul.Title;
import com.ujian.main.modul.TitleRowMapper;
import com.ujian.main.modul.Worker;
import com.ujian.main.modul.WorkerRowMapper;

@RestController
@RequestMapping("ujian")
public class Controller {
	
	@Autowired
	JdbcTemplate jdbc;
	
	public List<Worker> getSalaryTinggi(){
		
		String sql="SELECT * FROM worker ORDER BY salary DESC LIMIT 5";
		
		List <Worker> worker =jdbc.query(sql, new WorkerRowMapper());
		
		return worker; 
	}
	
	public List<Worker> getSalarySama(int salary){
		
		String sql=("Select * FROM worker where salary="+salary);
		
		List <Worker> worker =jdbc.query(sql, new WorkerRowMapper());
		
		return worker; 
	}
	
	public List<Worker> getDepartment(String department){
		
		String sql=("Select * FROM worker where department="+department+"");
		
		List <Worker> worker =jdbc.query(sql, new WorkerRowMapper());
		
		return worker; 
	}
	
	@GetMapping("/getSalaryTinggi")
	public List<Worker> getAllWorker(){
		return getSalaryTinggi();
	}
	
	@GetMapping("/getSalarySama/{salary}")
	public List<Worker> getAllWorkerSama(@PathVariable int salary){
		return getSalarySama(salary);
	}
	
	@GetMapping("/getDepartment/{department}")
	public List<Worker> getDepartmentAll(@PathVariable String department){
		return getDepartment(department);
	}
	
	
	public String insertWorker(Worker worker) {
		
		int sukses = jdbc.update("insert into worker(worker_id,first_name,last_name,salary,joining_date,department) values"
				+ "("+worker.getWorker_id()+",'"+worker.getFirst_name()+"','"+worker.getLast_name()+"','"+worker.getSalary()+"','"+worker.getJoining_date()+"','"+worker.getDepartment()+"')");
		String hasil;
		
		if(sukses ==1) {
			hasil = "Data Berhasil";
		}else {
			hasil="gagal";
		}
		return hasil;
		}
	
	@PostMapping("/PostWorker")
	public String add(@RequestBody Worker worker) {
		return insertWorker(worker); 
	}
	
	public String insertBonus(Bonus bonus) {
		
		int sukses = jdbc.update("insert into bonus(worker_ref_id,bonus_date,bonus_amount) values"
				+ "("+bonus.getWorker_ref_id()+",'"+bonus.getBonus_date()+"','"+bonus.getBonus_amount()+"')");
		String hasil;
		
		if(sukses ==1) {
			hasil = "Data Berhasil";
		}else {
			hasil="gagal";
		}
		return hasil;
		}
	
	@PostMapping("/PostBonus")
	public String add(@RequestBody Bonus bonus) {
		return insertBonus(bonus); 
	}
	
	public String insertTitle(Title title) {
		
		int sukses = jdbc.update("insert into title(worker_ref_id,worker_title,affected_from) values"
				+ "("+title.getWorker_ref_id()+",'"+title.getWorker_title()+"','"+title.getAffected_from()+"')");
		String hasil;
		
		if(sukses ==1) {
			hasil = "Data Berhasil";
		}else {
			hasil="gagal";
		}
		return hasil;
		}
	
	@PostMapping("/PosTitle")
	public String add(@RequestBody Title title) {
		return insertTitle(title); 
	}
	
	public String deleteWorker(int worker_id) { 
		int sukses = jdbc.update("DELETE FROM `worker` WHERE worker_id="+worker_id);
		String hasil;
		
		if(sukses ==1) {
			hasil = "Data Berhasil di hapus";
		}else {
			hasil="gagal dihapus";
		}
		return hasil;
		
	}
	
	@DeleteMapping("/deletWorker/{worker_id}")
	public String delete(@PathVariable int  worker_id) {
	  return deleteWorker(worker_id);
	}
	
	public String deleteBonus(int worker_ref_id) { 
		int sukses = jdbc.update("DELETE FROM `bonus` WHERE worker_ref_id="+worker_ref_id);
		String hasil;
		
		if(sukses ==1) {
			hasil = "Data Berhasil di hapus";
		}else {
			hasil="gagal dihapus";
		}
		return hasil;
		
	}
	
	@DeleteMapping("/deletBonus/{worker_ref_id}")
	public String deleteBonusId(@PathVariable int  worker_ref_id) {
	  return deleteBonus(worker_ref_id);
	}
	
	public String deleteTitle(int worker_ref_id) { 
		int sukses = jdbc.update("DELETE FROM `title` WHERE worker_ref_id="+worker_ref_id);
		String hasil;
		
		if(sukses ==1) {
			hasil = "Data Berhasil di hapus";
		}else {
			hasil="gagal dihapus";
		}
		return hasil;
		
	}
	
	@DeleteMapping("/deletTitle/{worker_ref_id}")
	public String deleteTitleId(@PathVariable int  worker_ref_id) {
	  return deleteTitle(worker_ref_id);
	}
	
	public int updateWorker(int worker_id,Worker worker) {
		return jdbc.update("UPDATE worker set `first_name`='"+ worker.getFirst_name() +"',`last_name`='"+ worker.getLast_name() +"',"
				+ "`salary`="+ worker.getSalary() + ",`joining_date`='"+ worker.getJoining_date() +"',`department`='"+ worker.getDepartment() +"' where worker_id ="+ worker.getWorker_id() +"");
	}
	
	@PutMapping("/updateWorker/{worker_id}")
	public ResponseEntity<?> updateWorkerId(@RequestBody Worker worker ,@PathVariable int worker_id){
		try {
			
			updateWorker(worker_id,worker);
			return new ResponseEntity<>(HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			// TODO: handle exception
		}
		
	}
	
	public int updateBonus(int worker_ref_id,Bonus bonus) {
		return jdbc.update("UPDATE bonus set `bonus_date`='"+ bonus.getBonus_date() +"',`bonus_amount`="+ bonus.getBonus_amount() +" where worker_ref_id = "+bonus.getWorker_ref_id()+"");
	}
	
	@PutMapping("/updateBonus/{worker_ref_id}")	
	public ResponseEntity<?> updateBonusId(@RequestBody Bonus bonus ,@PathVariable int worker_ref_id){
		try {
			
			updateBonus(worker_ref_id,bonus);
			return new ResponseEntity<>(HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			// TODO: handle exception
		}
		
	}
	
	public int updateTitle(int worker_ref_id,Title title) {
		return jdbc.update("UPDATE title set `worker_title`='"+ title.getWorker_title() +"',`affected_from`='"+ title.getAffected_from() +"' where worker_ref_id = "+title.getWorker_ref_id()+"");
	}
	
	@PutMapping("/updateTitle/{worker_ref_id}")	
	public ResponseEntity<?> updateBonusId(@RequestBody Title title ,@PathVariable int worker_ref_id){
		try {
			
			updateTitle(worker_ref_id,title);
			return new ResponseEntity<>(HttpStatus.OK);
			
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			// TODO: handle exception
		}
		
	}
	
	
}
