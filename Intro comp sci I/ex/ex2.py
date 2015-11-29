#by Albion Fung
# Sept 28, 2015
# V 0.1
# Global variables. Feel free to play around with these
# but please return them to their original values before you submit.
a0_weight = 5
a1_weight = 7
a2_weight = 8
term_tests_weight = 20
exam_weight = 45
exercises_weight = 10
quizzes_weight = 5
a0_max_mark = 25
a1_max_mark = 50
a2_max_mark = 100
term_tests_max_mark = 50
exam_max_mark = 100
exercises_max_mark = 10
quizzes_max_mark = 5
exam_pass_mark = 40
overall_pass_mark = 50


def get_max(component_name):
    '''(str) -> float
    Given the name of a course component (component_name),
    return the maximum mark for that component. This is used to allow the GUI
    to display the "out of" text beside each input field.
    REQ: component_name must be one of: a0,a1,a2,exerises,midterm,exam
    >>> get_max('a0')
    25
    >>> get_max('exam')
    100
    REQ: component_name in {'a0', 'a1', 'a2', 'exercises', 'term tests',
    'quizzes', 'exam'}
    '''
    # DO NOT EDIT THIS FUNCTION. This function exists to allow the GUI access
    # to some of the global variables. You can safely ignore this function
    # for the purposes of E2.
    if(component_name == 'a0'):
        result = a0_max_mark
    elif(component_name == 'a1'):
        result = a1_max_mark
    elif(component_name == 'a2'):
        result = a2_max_mark
    elif(component_name == 'exercises'):
        result = exercises_max_mark
    elif(component_name == 'term tests'):
        result = term_tests_max_mark
    elif(component_name == 'quizzes'):
        result = quizzes_max_mark
    else:
        result = exam_max_mark

    return result

def contribution(raw_mark, max_mark, weight):
    ''' (float, float, float) -> float
    Given a piece of work where the student earned raw_mark marks out of a
    maximum of max_marks marks possible, return the number of marks it
    contributes to the final course mark if this piece of work is worth weight
    marks in the course marking scheme.
    >>> raw_contribution(13.5, 15, 10)
    9.0
    REQ: raw_mark >=0
    REQ: max_mark > 0
    REQ: weight >= 0
    '''
    #return the contribution, in a float, of the mark
    return((raw_mark/max_mark)*weight)

def percentage(raw_mark,max_mark):
    '''(float,float)->float
    Enter the mark the student got as raw_mark and maximum possible mark in 
    max_mark to recieve a the student's mark in percentages.
    >>> percentage(5,10)
    50.0
    REQ: raw_mark >=0
    REQ: max_mark >0
    '''
    #making sure the variables are all float, and not restricted to int
    raw_mark=float(raw_mark)
    max_mark=float(max_mark)
    #return the percentatge, in terms of the maximum mark, as a float
    return((raw_mark/max_mark)*100)

def term_work_mark(a0_mark,a1_mark,a2_mark,exercises_mark,quizzes_mark,term_tests_mark):
    '''(float,float,float,float,float,float)->float
    Enter the marks in this specific order:
    assignemnt 0 mark, assignment 1 mark, assignment 2 mark, exercise marks, 
    quizz marks, term tests marks
    To recieve a float that indicates your final coursework mark.
    >>> term_work_mark(25, 50, 100, 10, 5, 50)
    55.0
    >>> term_work_mark(20, 45, 70, 8, 4, 40)
    43.9
    REQ:a0_mark,a1_mark,a2_mark,exercises_mark,quizzes_mark,term_tests_mark >=0
    '''
    #use function contribution() to find out how much each attribute contributes
    #to the final course mark
    a0_mark=contribution(a0_mark,a0_max_mark,a0_weight)
    a1_mark=contribution(a1_mark,a1_max_mark,a1_weight)
    a2_mark=contribution(a2_mark,a2_max_mark,a2_weight)
    exercises_mark=contribution(exercises_mark,exercises_max_mark,exercises_weight)
    quizzes_mark=contribution(quizzes_mark,quizzes_max_mark,quizzes_weight)
    term_tests_mark=contribution(term_tests_mark,term_tests_max_mark,term_tests_weight)
    #sum up the contributions of each attribute and return the final mark
    return(((((a0_mark+a1_mark)+a2_mark)+exercises_mark)+quizzes_mark)+term_tests_mark)

def final_mark(a0_mark,a1_mark,a2_mark,exercises_mark,quizzes_mark,term_tests_mark,exam_mark):
    '''(float,float,float,float,float,float)->float
    Enter the marks in this specific order:
    assignemnt 0 mark, assignment 1 mark, assignment 2 mark, exercise marks, 
    quizz marks, term tests marks, final exam mark
    To recieve a float that indicates your final coursework mark.
    >>> final_mark(25, 50, 100, 10, 5, 50,100)
    100.0
    >>> final_mark(20, 45, 70, 8, 4, 40, 73)
    76.75
    REQ:a0_mark,a1_mark,a2_mark,exercise_mark,quizzes_mark,term_tests_mark,exam_mark >=0
    '''
    #use function tern_work_mark() to process the first six parameters, so we only
    #have to calculate the mark contribution of 1 parameter
    term_mark=term_work_mark(a0_mark,a1_mark,a2_mark,exercises_mark,quizzes_mark,term_tests_mark)
    #calculate contribution marks of the final exam by using function contribution
    exam_mark=contribution(exam_mark,exam_max_mark,exam_weight)
    #sum up and return the final mark of the course
    term_mark+=exam_mark
    return(term_mark)

def is_pass(a0_mark,a1_mark,a2_mark,exercises_mark,quizzes_mark,term_tests_mark,exam_mark):
    '''(float,float,float,float,float,float)->float
    Enter the marks in this specific order:
    assignemnt 0 mark, assignment 1 mark, assignment 2 mark, exercise marks, 
    quizz marks, term tests marks, final exam mark
    To recieve a boolean that is 'True' if the student passed the course or
    'False' if the student fails the course.
    >>> is_pass(20, 45, 70, 8, 4, 40, 41)
    True
    >>> is_pass(20, 45, 70, 8, 4, 40, 39)
    False
    >>> is_pass(10, 21, 12, 2, 1, 15, 23)
    False
    REQ:a0_mark,a1_mark,a2_mark,exercise_mark,quizzes_mark,term_tests_mark,exam_mark >=0
    '''
    #use function final_mark to find the student's final mark
    mark=final_mark(a0_mark,a1_mark,a2_mark,exercises_mark,quizzes_mark,term_tests_mark,exam_mark)
    #compare it to the passing mark, and return if the student passes or not
    return(mark>overall_pass_mark)