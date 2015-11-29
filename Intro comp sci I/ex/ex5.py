# Albion Fung
# Oct 26, 2015
# V0.0.1


def function_names(file):
    '''(io.TextIOWrapper) -> list of str
    The function, given a file already opened, will return
    a list, with string elements, of different functions in the
    python file. The function does not open nor close the file.
    REQ: io.TextIOWrapper be opened and defined before hand
    REQ: io.TextIOWrapper reference be a file of Python code

    in ex4.py:
    def chocolate(a, b):
       return True
    end of file

    >>>file = open("ex4.py", "r")
    >>>function_names(file)
    ['chocolate']
    '''
    # Initialize the list that stores function names
    func_list = []
    # read the first line so while loop is true
    line = file.readline()
    # read until end of file
    while(line != ""):
        # if the line read is the header of a function,
        # take the name of the function by taking characters
        # after "def " and before "("
        if((line[:3]) == "def"):
            line = line[4:]
            line = line.split("(")
            func_list.insert(len(func_list), line[0])
        # read a new line before repeating the loop
        line = file.readline()
    # return the list of functions
    return func_list


def justified(file):
    '''(io.TextIOWrapper) -> boolean
    The function, given a file already opened, will return
    a boolean. If the file is all left justified (ie, no spaces
    or tabs before each line in the file), the function returns true.
    Else, the function returns false.
    The function does not open nor close the file.
    REQ: io.TextIOWrapper reference be a file of text document (not rich)

    in test.txt:
       1002444123 Cat fat mat LEC01 17.1
    1233219876    Pat fat mat LEC01 17.3
    end of file

    >>>file = open("test.txt", "r")
    >>>justified(file)
    False

    in test.txt:
    1234
    2345
    end of file
    >>>file = open("test.txt", "r")
    >>>justified(file)
    True

    in test.txt:
    1234
    \t12345
    end of file
    >>>file = open("test.txt", "r")
    >>>justified(file)
    False
    '''
    left_just = True
    line = 'A'
    while((left_just) and (line != '')):
        line = file.readline()
        if(line[:1] == " " or line[:1] == "\t"):
            left_just = False
    return left_just


def section_average(file, sec):
    '''(io.TextIOWrapper, str) -> float
    The function, given a file already opened, will return
    a float which would be the average mark of the desired section.
    The function does not open nor close the file.
    REQ: str be a string exactly as "LECXX", where xx are 2 numbers
    REQ: io.TextIOWrapper reference be a file of text document (not rich)

    in mark.txt:
    1002444123 Cat fat mat LEC01 17.1
    1233219876    Pat fat mat LEC01 17.3
    end of file

    >>>file = open("mark.txt", "r")
    >>>section_average(file, "LEC01")
    17.2
    >>>flie.close()
    >>>file = open("mark.txt", "r")
    >>>section_average(file, "LEC02")
    >>>file.close()
    >>>file = open("mark.txt", "r")
    >>>section_average(file, "lec01")
    17.2
    '''
    # initialize required variables
    tot_mark = 0.0
    students = 0.0
    # read the first line so the while loop is true
    line = file.readline()
    # read til end of file
    while(line != ""):
        # remove the useless part before "LECXX" and the mark
        lecline = line.split("LEC")
        lecline = lecline[1]
        # check if the code after "LEC" mataches with what's desired
        # if it's the desired course, add the desired mark to a total mark
        if(sec[3:] == lecline[:2]):
            lecline = lecline[2:]
            tot_mark += float(lecline.strip())
            students += 1
        # read a new line before loop
        line = file.readline()
    # if at least 1 student was in the section desired, return the average
    if(students > 0):
        average = tot_mark / students
        return average
    # otherwise, return none
    else:
        return None
