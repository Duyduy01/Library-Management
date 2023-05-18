package com.example.demo;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class BookController2 {
	@GetMapping("/books")
	public List<Book> getBooks(Model model) throws IOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Book> books = new ArrayList<Book>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qltv", "root", "123456");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from books");
			
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String title = resultSet.getString("title");
				String author = resultSet.getString("author");
				String discription = resultSet.getString("discription");
				String dateofissue = resultSet.getString("dateofissue");
				String pages = resultSet.getString("pages");
				String category = resultSet.getString("category");

				books.add(new Book(id, title, author, discription, dateofissue, pages, category));
			}
		} // End of try block
		catch (Exception e) {
			e.printStackTrace();
		}

		return books;
	}
	@GetMapping("/book/{id}")
	public Book getBook(Model model, @PathVariable String id) {
		model.addAttribute("id", id);
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet result = null;

		Book book = new Book();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qltv", "root", "123456");
			ps = connection.prepareStatement("select * from books where id = ?");
			ps.setInt(1, Integer.valueOf(id));
			result = ps.executeQuery();
			while (result.next()) {
				book.setId(result.getInt("id"));
				book.setTitle(result.getString("title"));
				book.setAuthor(result.getString("author"));
				book.setDiscription(result.getString("discription"));
				book.setDateofissue(result.getString("dateofissue"));
				book.setPages(result.getString("pages"));
				book.setCategory(result.getString("category"));

			}
		} // End of try block
		catch (Exception e) {
			e.printStackTrace();
		}

		return book;
	}

	@PostMapping("/book/save")
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet generatedKeys = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qltv", "root", "123456");
			ps = connection.prepareStatement("INSERT INTO books (title, author, discription, dateofissue, pages, category) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getDiscription());
			ps.setString(4, book.getDateofissue());
			ps.setString(5, book.getPages());
			ps.setString(6, book.getCategory());
			int result = ps.executeUpdate();

			if (result > 0) {
				generatedKeys = ps.getGeneratedKeys();
				if (generatedKeys.next()) {
					int id = generatedKeys.getInt(1);
					book.setId(id);
					return ResponseEntity.ok(book);
				}
			}
			return null;

//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (Exception e) {
			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			return null;
		}
	}


	@PutMapping("/book/update/{id}")
	public String updateBook(@RequestBody Book book, @PathVariable String id) {
		Connection connection = null;
		PreparedStatement ps = null;
		int result = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qltv", "root", "123456");
			ps = connection.prepareStatement("UPDATE books SET title=?, author=?, discription=?, dateofissue=?, pages=?, category=? WHERE id=?");
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getDiscription());
			ps.setString(4, book.getDateofissue());
			ps.setString(5, book.getPages());
			ps.setString(6, book.getCategory());
			ps.setInt(7, Integer.valueOf(id));
			result = ps.executeUpdate();
			ps.close();
			connection.close();
			if (result > 0) {
				return "Update success";
			} else {
				return "No record found for the given ID";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	@DeleteMapping("/book/delete/{id}")
	public String deleteBook(@PathVariable String id) {
		Connection connection = null;
		PreparedStatement ps = null;
		int result = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qltv", "root", "123456");
			ps = connection.prepareStatement("DELETE FROM books WHERE id=?");
			ps.setInt(1, Integer.valueOf(id));
			result = ps.executeUpdate();
			ps.close();
			connection.close();
			if (result > 0) {
				return "Delete success";
			} else {
				return "No record found for the given ID";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}


}
