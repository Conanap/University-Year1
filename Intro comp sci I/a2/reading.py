# By Albion Ka Hei Fung
# Nov 25, 2015
# v 0.0.1

import glob
from database import *

# a table is a dict of {str:list of str}.
# The keys are column names and the values are the values
# in the column, from top row to bottom row.

# A database is a dict of {str:table},
# where the keys are table names and values are the tables.

# YOU DON'T NEED TO KEEP THE FOLLOWING CODE IN YOUR OWN SUBMISSION
# IT IS JUST HERE TO DEMONSTRATE HOW THE glob CLASS WORKS. IN FACT
# YOU SHOULD DELETE THE PRINT STATEMENT BEFORE SUBMITTING
file_list = glob.glob('*.csv')
# Write the read_table and read_database functions below


def read_table(file_name):
    '''(str) -> Table
    Given the file, the function reads the file and turns the information
    into a table.
    in book.csv:
    b.name,b.price
    Chocolate,15
    Bob the Sleeper, 20
    >>>read_table('book.csv')
    {'b.name': ['Chocolate', 'Bob the Sleeper'], 'b.price': ['15', '20']}
    '''
    # open file
    _file = open(file_name, 'r')
    # make a new table
    _table = Table()
    # read the first line and make it the header of the table
    _line = _file.readline()
    # adding header
    _table.add_header(_line)
    # read next line
    _line = _file.readline()
    _line = _line.strip()
    # as long as there is still data
    while(_line != ""):
        # add the data to the next row
        _table.add_row(_line)
        # read next line
        _line = _file.readline()
        # strip it so '\n' doesn't get passed
        _line = _line.strip()
    # close the file
    _file.close()
    # return completed table
    return _table


def read_database():
    '''() -> Database
    Reads all the .csv files in the current directory. The fuction creates a
    database from these file, where the name of the file is the name of the
    table, and the table contents are from within the table_name.csv file.
    The database is then returned.
    In local directory:
       bamboo.csv:
         b.name,b.height
         waka,tall
         kawa,short
       dance.csv:
         d.type,d.music
         trollol,sandstorm
         hiphop,jazz
    >>>read_database()
    {'bamboo': {'b.name': ['waka', 'kawa'], 'b.height': ['tall', 'short']},
     'dance': {'d.type': ['trollol', 'hiphop'], 'd.music': ['sandstorm',
     'jazz']}}
    '''
    # get the list of files from the current directory with .csv extension
    _file_list = glob.glob('*.csv')
    # create a new list with modified names
    _new_file_list = []
    # for each file name, take away .csv extension
    for _file in _file_list:
        # add it to the new list
        _new_file_list += [_file[:-4]]
    # make a new dictionary for database
    _database = Database()
    # make a new table and add it to the database for each file
    for _file in _new_file_list:
        _database.add_table(_file, read_table(_file + '.csv'))
    # return the database
    return _database
