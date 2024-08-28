<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <h2>Registration</h2>

    <form name="registrationForm" method="post" action="registrationProcessor.jsp">
        <label for="name">Name:</label>
        <input type="text" name="name" 
        onclick="this.value='register.name'"required>
        <br>

        <label for="email">Email:</label>
        <input type="email" name="email" onclick="'register.email'"  required>
        <br>

        <label for="phone">Phone Number:</label>
        <input type="text" name="phone" onclick="this.value='register.phoneNumber'"  required><br>

        <label for="dob">DOB:</label>
        <input type="date" name="dob"  onclick="this.value='register.dob'"
         required><br>

        <label for="category">Category:</label>
        <select name="category" onclick='register.category'">
        
        </select><br>

        <label for="securityQ1">Security Question 1:</label>
        <input type="text" name="securityQ1" required><br>

        <label for="securityQ2">Security Question 2:</label>
        <input type="text" name="securityQ2" required><br>

        <label for="securityQ3">Security Question 3:</label>
        <input type="text" name="securityQ3" required><br>

        <label for="username">User Name:</label>
        <input type="text" name="username" required><br>

        <label for="password">Password:</label>
        <input type="password" name="password" required><br>

        <label for="confirmPassword">Confirm Password:</label>
        <input type="password" name="confirmPassword" required><br>

        <input type="submit" value="Register">
    </form>
</body>
</html>
