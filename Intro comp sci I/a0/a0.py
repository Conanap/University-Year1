# Code for working with word search puzzles
#
# Do not modify the existing code
#
# Complete the tasks below marked by *task*
#

PUZZLE1 = '''
glkutqyu
onnkjoaq
uaacdcne
gidiaayu
urznnpaf
ebnnairb
xkybnick
ujvaynak
'''

PUZZLE2 = '''
fgbkizpyjohwsunxqafy
hvanyacknssdlmziwjom
xcvfhsrriasdvexlgrng
lcimqnyichwkmizfujqm
ctsersavkaynxvumoaoe
ciuridromuzojjefsnzw
bmjtuuwgxsdfrrdaiaan
fwrtqtuzoxykwekbtdyb
wmyzglfolqmvafehktdz
shyotiutuvpictelmyvb
vrhvysciipnqbznvxyvy
zsmolxwxnvankucofmph
txqwkcinaedahkyilpct
zlqikfoiijmibhsceohd
enkpqldarperngfavqxd
jqbbcgtnbgqbirifkcin
kfqroocutrhucajtasam
ploibcvsropzkoduuznx
kkkalaubpyikbinxtsyb
vjenqpjwccaupjqhdoaw
'''


def rotate_puzzle(puzzle):
    '''(str) -> str
    Return the puzzle rotated 90 degrees to the left.
    '''

    raw_rows = puzzle.split('\n')
    rows = []
    # if blank lines or trailing spaces are present, remove them
    for row in raw_rows:
        row = row.strip()
        if row:
            rows.append(row)

    # calculate number of rows and columns in original puzzle
    num_rows = len(rows)
    num_cols = len(rows[0])

    # an empty row in the rotated puzzle
    empty_row = [''] * num_rows

    # create blank puzzle to store the rotation
    rotated = []
    for row in range(num_cols):
        rotated.append(empty_row[:])
    for x in range(num_rows):
        for y in range(num_cols):
            rotated[y][x] = rows[x][num_cols - y - 1]

    # construct new rows from the lists of rotated
    new_rows = []
    for rotated_row in rotated:
        new_rows.append(''.join(rotated_row))

    rotated_puzzle = '\n'.join(new_rows)

    return rotated_puzzle


def lr_occurrences(puzzle, word):
    '''(str, str) -> int
    Return the number of times word is found in puzzle in the
    left-to-right direction only.

    >>> lr_occurrences('xaxy\nyaaa', 'xy')
    1
    '''
    return puzzle.count(word)

# ---------- Your code to be added below ----------

# *task* 3: write the code for the following function.
# We have given you the header, type contract, example, and description.


def total_occurrences(puzzle, word):
    '''(str, str) -> int
    Return total occurrences of word in puzzle.
    All four directions are counted as occurrences:
    left-to-right, top-to-bottom, right-to-left, and bottom-to-top.

    >>> total_occurrences('xaxy\nyaaa', 'xy')
    2
    '''
    # your code here
    # declaring necessary variables for the function
    tot_occur = 0
    new_puzzle = puzzle
    # find out how many times the name occurs in the puzzle in a specific
    # direction
    new_puzzle = rotate_puzzle(new_puzzle)
    tot_occur += lr_occurrences(new_puzzle, word)
    new_puzzle = rotate_puzzle(new_puzzle)
    tot_occur += lr_occurrences(new_puzzle, word)
    new_puzzle = rotate_puzzle(new_puzzle)
    tot_occur += lr_occurrences(new_puzzle, word)
    new_puzzle = rotate_puzzle(new_puzzle)
    tot_occur += lr_occurrences(new_puzzle, word)
    # return the result
    return tot_occur
# *task* 5: write the code for the following function.
# We have given you the function name only.
# You must follow the design recipe and complete all parts of it.
# Check the handout for what the function should do.


def in_puzzle_horizontal(puzzle, word):
    '''(str, str) -> bool
    REQ: puzzle and word is a string
    Given the word puzzle and the word, the function looks for
    if there is an occurrence in the horizontal direction (both).
    It will return true if it does occur in 1 or both of the horizontal
    directions.
    >>>in_puzzle_horizontal('aabc\naaaa', 'abc')
    True
    >>>in_puzzle_horizontal('acba\naaaa', 'abc')
    True
    >>>in_puzzle_horizontal('aaa\nbaa\ncaa', 'abc')
    False
    >>>in_puzzle_horizontal('aabc\ncbaa', 'abc')
    True
    '''
    # declaring necessary variables for the funciton
    new_puzzle = puzzle
    hori_occur = 0
    # find the occurrences in the horizontal direction
    hori_occur += lr_occurrences(new_puzzle, word)
    new_puzzle = rotate_puzzle(rotate_puzzle(new_puzzle))
    hori_occur += lr_occurrences(new_puzzle, word)
    # if the value of hori_occur is not 0, return true. Return false
    # otherwise
    return (hori_occur != 0)

# *task* 8: write the code for the following function.
# We have given you the function name only.
# You must follow the design recipe and complete all parts of it.
# Check the handout for what the function should do.


def in_puzzle_vertical(puzzle, word):
    '''(str, str) -> bool
    REQ: puzzle and word is a string
    Given the word puzzle and the word, the function looks for
    if there is an occurrence in the horizontal direction (both).
    It will return true if it does occur in 1 or both of the horizontal
    directions.
    >>>in_puzzle_horizontal('aabc\naaaa\naaaa', 'abc')
    False
    >>>in_puzzle_horizontal('aaa\nbaa\ncaa', 'abc')
    True
    >>>in_puzzle_horizontal('caa\nbaa\naaa', 'abc')
    True
    >>>in_puzzle_horizontal('caa\nbba\naca', 'abc')
    True
    '''
    # declaring necessary variables for the funciton
    new_puzzle = puzzle
    vert_occur = 0
    # find the occurrences in the horizontal direction
    new_puzzle = (rotate_puzzle(new_puzzle))
    vert_occur += lr_occurrences(new_puzzle, word)
    new_puzzle = rotate_puzzle(rotate_puzzle(new_puzzle))
    vert_occur += lr_occurrences(new_puzzle, word)
    # if the value of vert_occur is not 0, return true. Return false
    # otherwise
    return (vert_occur != 0)

# *task* 9: write the code for the following function.
# We have given you the function name only.
# You must follow the design recipe and complete all parts of it.
# Check the handout for what the function should do.


def in_puzzle(puzzle, word):
    '''(str, str) -> bool
    REQ: puzzle and word are strings
    Given the word puzzle and the word, the function returns True if
    the word can be found in the puzzle at least once. The function
    returns False if the word cannot be found in vertical or horizontal
    direciton.
    >>>in_puzzle('aaa\naaa', 'abc')
    False
    >>>in_puzzle('abc\naaa', 'abc')
    True
    >>>in_puzzle('aaa\nbaa\ncaa', 'abc')
    True
    '''
    # see if the word is found in the word puzzle at all
    hori_occur = in_puzzle_horizontal(puzzle, word)
    vert_occur = in_puzzle_vertical(puzzle, word)
    # if the word is found in the puzzle at least once, the variable
    # will become True
    occur_in_puzzle = (hori_occur or vert_occur)
    return occur_in_puzzle

# *task* 10: write the code for the following function.
# We have given you only the function name and parameters.
# You must follow the design recipe and complete all parts of it.
# Check the handout for what the function should do.


def in_exactly_one_dimension(puzzle, word):
    '''(str, str) -> bool
    REQ: puzzle and word must be strings
    Given the puzzle and the word, the function returns True if
    the word is only found either horizontally or vertically;
    False if not found or found in both directions.
    >>>in_exactly_one_dimension('aaa\nabc','abc')
    True
    >>>in_exactly_one_dimension('aaa\nabc\naca','abc')
    False
    >>>in_exactly_one_dimension('aaa\naaa','abc')
    False
    >>>in_exactly_one_dimension('aaa\naba\naca','abc')
    True
    >>>in_exactly_one_dimension('abc\nabc','abc')
    True
    '''
    # see if the word is found in the word puzzle at all
    hori_occur = in_puzzle_horizontal(puzzle, word)
    vert_occur = in_puzzle_vertical(puzzle, word)
    # if the word is found in the puzzle in exactly 1 dimension,
    # the variable will become True
    return (hori_occur != vert_occur)

# *task* 11: write the code for the following function.
# We have given you only the function name and parameters.
# You must follow the design recipe and complete all parts of it.
# Check the handout for what the function should do.


def all_horizontal(puzzle, word):
    '''(str, str) -> bool
    REQ: puzzle and word must be strings
    Given the puzzle and the word, the function returns True if
    the word is only found horizontally or cannot be found;
    False if found in both directions.
    >>>all_horizontal('aaa\nabc','abc')
    True
    >>>all_horizontal('aaa\nabc\naca','abc')
    False
    >>>all_horizontal('aaa\naaa','abc')
    True
    >>>all_horizontal('aaa\naba\naca','abc')
    False
    >>>all_horizontal('abc\nabc','abc')
    True
    '''
    # see if the word is found in the 2 different dimensions of the
    # word puzzle at all
    hori_occur = in_puzzle_horizontal(puzzle, word)
    vert_occur = in_puzzle_vertical(puzzle, word)
    # if the word is not found in the puzzle or only in the horizontal,
    # the variable will become True
    hori_only = hori_occur and (not vert_occur)
    not_found = (not hori_occur) and (not vert_occur)
    return (hori_only or not_found)

# *task* 12: write the code for the following function.
# We have given you only the function name and parameters.
# You must follow the design recipe and complete all parts of it.
# Check the handout for what the function should do.


def at_most_one_vertical(puzzle, word):
    '''(str, str) -> bool
    REQ: puzzle and word must be strings
    Given the puzzle and the word, the function returns True if
    the word is only found once and is found vertically;
    False if otherwise.
    >>>at_most_one_vertical('aaa\nabc','abc')
    False
    >>>at_most_one_vertical('aaa\nabc\naca','abc')
    False
    >>>at_most_one_vertical('aaa\naaa','abc')
    False
    >>>at_most_one_vertical('aaa\naba\naca','abc')
    True
    >>>at_most_one_vertical('abc\nbcb\naca','abc')
    False
    '''
    # declaring necessary variables for the funciton
    new_puzzle = puzzle
    vert_occur = 0
    hori_occur = 0
    # find the occurrences in the horizontal direction
    new_puzzle = (rotate_puzzle(new_puzzle))
    vert_occur += lr_occurrences(new_puzzle, word)
    new_puzzle = rotate_puzzle(rotate_puzzle(new_puzzle))
    vert_occur += lr_occurrences(new_puzzle, word)
    # resetting direction of new_puzzle
    new_puzzle = puzzle
    # find the occurrences in the horizontal direction
    hori_occur += lr_occurrences(new_puzzle, word)
    new_puzzle = rotate_puzzle(rotate_puzzle(new_puzzle))
    hori_occur += lr_occurrences(new_puzzle, word)
    # if the total occurrences is 1 and only occurred in the
    # vertical direction, return True
    return (((vert_occur + hori_occur) == 1) and (hori_occur == 0))


def do_tasks(puzzle, name):
    '''(str, str) -> NoneType
    puzzle is a word search puzzle and name is a word.
    Carry out the tasks specified here and in the handout.
    '''

    # *task* 1a: add a print call below the existing one to print
    # the number of times that name occurs in the puzzle left-to-right.
    # Hint: one of the two starter functions defined above will be useful.

    # the end='' just means "Don't start a newline, the next thing
    # that's printed should be on the same line as this text
    print('Number of times', name, 'occurs left-to-right: ', end='')
    # your print call here
    new_puzzle = puzzle
    print(lr_occurrences(new_puzzle, name))

    # *task* 1b: add code that prints the number of times
    # that name occurs in the puzzle top-to-bottom.
    # (your format for all printing should be similar to
    # the print statements above)
    # Hint: both starter functions are going to be useful this time!
    new_puzzle = rotate_puzzle(puzzle)
    print('Number of times', name, 'occurs top-to-bottom: ', end='')
    print(lr_occurrences(new_puzzle, name))

    # *task* 1c: add code that prints the number of times
    # that name occurs in the puzzle right-to-left.
    new_puzzle = rotate_puzzle(rotate_puzzle(puzzle))
    print('Number of times', name, 'occurs right-to-left: ', end='')
    print(lr_occurrences(new_puzzle, name))

    # *task* 1d: add code that prints the number of times
    # that name occurs in the puzzle bottom-to-top.
    new_puzzle = rotate_puzzle(rotate_puzzle(rotate_puzzle(puzzle)))
    print('Number of times', name, 'occurs bottom-to-top: ', end='')
    print(lr_occurrences(new_puzzle, name))

    # *task* 4: print the results of calling total_occurrences on
    # puzzle and name.
    # Add only one line below.
    # Your code should print a single number, nothing else.
    print(total_occurrences(puzzle, name))

    # *task* 6: print the results of calling in_puzzle_horizontal on
    # puzzle and name.
    # Add only one line below. The code should print only True or False.
    print(in_puzzle_horizontal(puzzle, name))

do_tasks(PUZZLE1, 'brian')

# *task* 2: call do_tasks on PUZZLE1 and 'nick'.
# Your code should work on 'nick' with no other changes made.
# If it doesn't work, check your code in do_tasks.
# Hint: you shouldn't be using 'brian' anywhere in do_tasks.
do_tasks(PUZZLE1, 'nick')

# *task* 7: call do_tasks on PUZZLE2 (that's a 2!) and 'nick'.
# Your code should work on the bigger puzzle with no changes made to do_tasks.
# If it doesn't work properly, go over your code carefully and fix it.
do_tasks(PUZZLE2, 'nick')

# *task* 9b: print the results of calling in_puzzle on PUZZLE1 and 'nick'.
# Add only one line below. Your code should print only True or False.
print(in_puzzle(PUZZLE1, 'nick'))

# *task* 9c: print the results of calling in_puzzle on PUZZLE2 and 'anya'.
# Add only one line below. Your code should print only True or False.
print(in_puzzle(PUZZLE2, 'anya'))
