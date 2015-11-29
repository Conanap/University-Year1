# Albion Fung
# v0.0.1
# Nov 2, 2015


def copy_me(input_list):
    '''(list) -> list
    Given a list, the function does the following modifications
    without modifying the original input list:
    Capitalize all str
    Add 1 to all int and float
    Booleans are negated (false -> true, true -> false)
    Lists are replaced with 'List' (str)
    REQ: input must be list
    >>>copy_me(['aa',13,True, [1,2,3], 'bdf', 1.9])
    ['AA', 14, False, 'List', 'BDF', 2.9]
    >>>copy_me([])
    []
    '''
    # copy the list
    new_list = input_list[:]
    # loop through each element, see what type it is
    # and change it according to descrption
    for i in range(len(new_list)):
        # check for types
        # change a required
        if(type(new_list[i]) == str):
            new_list[i] = new_list[i].upper()
        elif((type(new_list[i]) == float) or (type(new_list[i]) == int)):
            new_list[i] += 1
        elif(type(new_list[i]) == bool):
            new_list[i] = not new_list[i]
        elif(type(new_list[i]) == list):
            new_list[i] = "List"
    # return the new list ater modification
    return new_list


def mutate_me(input_list):
    '''(list) -> None
    Given a list, the function does the following modifications
    to the original input list:
    Capitalize all str
    Add 1 to all int and float
    Booleans are negated (false -> true, true -> false)
    Lists are replaced with 'List' (str)
    REQ: input must be list
    >>>i = ['aa',13,True, [1,2,3], 'bdf', 1.9]
    >>>mutate_me(i)
    ['AA', 14, False, 'List', 'BDF', 2.9]
    >>>mutate_me([])
    '''
    # loop through each element, see what type it is
    # and change it according to descrption
    for i in range(len(input_list)):
        # check for types
        # change a required
        if(type(input_list[i]) == str):
            input_list[i] = input_list[i].upper()
        elif((type(input_list[i]) == float) or (type(input_list[i]) == int)):
            input_list[i] += 1
        elif(type(input_list[i]) == bool):
            input_list[i] = not input_list[i]
        elif(type(new_list[i]) == list):
            input_list[i] = "List"
