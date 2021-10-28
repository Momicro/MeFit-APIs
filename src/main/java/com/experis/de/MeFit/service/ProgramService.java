package com.experis.de.MeFit.service;

import com.experis.de.MeFit.models.Program;

//Program service interface
public interface ProgramService {

    //Function to create a program with nested data inside.
    Program createProgram(Program program);

    //Function to delete the program with nested data inside.
    void deleteProgram(Program program);
}
