# Albion Fung
# v0.0.1  Nov 7, 2015


def create_dict(_file):
    '''(io.TextIOWrapper) -> dict of {str: [str,str,str,int,str]}
    takes in a file handler, and returns a dictionary with the key as
    usernames, and the key maps to a list containing last name, first name,
    email, age and gender.
    REQ: file be in following format:
    1 line per user
    username FirstName LastName Age Gender(M/F) Email
    REQ: File be opened for reading. The function will not open nor close
    the file.
    Example file (t.txt):
    alink Alpha Link 18 M alphalink@zelda.com

    >>>file = open('t.txt', 'r')
    >>>user = create_dict(file)
    >>>user
    {'alink': ['Link', 'Alpha', 'alphlink@zelda.com', 18, 'M']}
    '''
    # note for developer:
    # [last name, first name, email, age, gender]
    # create a dictionary
    _username_to_data = {}
    # read the first line so the while loop will run
    _next_line = _file.readline()
    # if a new line exists
    while(_next_line != ''):
        # strip the line and make it a list of words
        _next_line = _next_line.strip()
        _next_line = _next_line.split()
        # assign words to corresponding variable
        # and into dicitonary
        _user_name = _next_line[0]
        _first_name = _next_line[1]
        _last_name = _next_line[2]
        _age = int(_next_line[3])
        _gender = _next_line[4]
        _email = _next_line[5]
        _info = [_last_name, _first_name, _email, _age, _gender]
        _username_to_data[_user_name] = _info
        # read the next line
        _next_line = _file.readline()
    # return the dictionary
    return _username_to_data


def update_field(_username_to_data, _username, _field, _new_value):
    '''(dict, str, str, str or int) -> NoneType
    Given the dictionary with the format:
    {username:[lastname, firstname, email, age, gender]}
    The function updates the username's specified field with the new value.
    REQ: _username_to_data be in the above format
    REQ: _username exists in the dictionary
    REQ: _field be 'LAST', 'FIRST', 'E-MAIL', 'GENDER' or 'AGE'. Must be
    exactly as written. Any other input will cause no change to the dictionary
    Example:
    >>>user = {'alink': ['Link', 'Alpha', 'alphlink@zelda.com', 18, 'M']}
    >>>update_field(user,'alink', 'FIRST','Beta')
    >>>user
    {'alink': ['Link', 'Beta', 'alphalink@zelda.com', 18, 'M']}
    '''
    # see what field they want to change
    if(_field == 'LAST'):
        _new_field = 0
    elif(_field == 'FIRST'):
        _new_field = 1
    elif(_field == 'E-MAIL'):
        _new_field = 2
    elif(_field == 'AGE'):
        _new_field = 3
    elif(_field == 'GENDER'):
        _new_field = 4
    else:
        _new_field = 5
    # change the desired field
    if(_new_field != 5):
        _username_to_data[_username][_new_field] = _new_value


def select(_username_to_data, _wanted_field, _check_field, _check_value):
    '''(dict, str, str, str or int) -> set
    Given the dictionary with the format:
    {username:[lastname, firstname, email, age, gender]}
    The function checks each user's _check_field field (eg, age), and see if it
    matches _check_value (eg, 18). If it matches, the user's _wanted_field (eg,
    first name) will be returned in a set, along with other matches. An example
    use for the function would be to find all users' first name who are 18
    years old.
    REQ: _username_to_data be in the above format
    REQ: _wanted_field and _check_field be 'LAST', 'FIRST', 'E-MAIL', 'GENDER'
    or 'AGE'. Must be exactly as written. Any other input will cause an empty
    set to be returned
    REQ: _check_value must match the type of _check_field. Example, if
    _check_field is 'FIRST', _check_value must be str. If _check_field is
    'AGE', _check_value must be int.
    Example:
    >>>user = {'alink': ['Link', 'Alpha', 'alphlink@zelda.com', 18, 'M'],
    'bbuilder': 'Builder', 'Bob The', 'bobthebuilder@cons.com, 18, 'M']}
    >>>select(user,'LAST', 'AGE',18)
    {'Link', 'Builder'}
    >>>select(user, 'FIRST', 'GENDER', 'F')
    set()
    >>>select(user, 'FIRST', 'GENDER', 'M')
    {'Alpha', 'Bob The'}
    '''
    # create an initial empty set
    selected = set()
    # see what field is the requirement
    if(_check_field == 'LAST'):
        _new_field = 0
    elif(_check_field == 'FIRST'):
        _new_field = 1
    elif(_check_field == 'E-MAIL'):
        _new_field = 2
    elif(_check_field == 'AGE'):
        _new_field = 3
    elif(_check_field == 'GENDER'):
        _new_field = 4
    else:
        _new_field = 5
    # see what field is desired
    if(_wanted_field == 'LAST'):
        _new_want_field = 0
    elif(_wanted_field == 'FIRST'):
        _new_want_field = 1
    elif(_check_field == 'E-MAIL'):
        _new_want_field = 2
    elif(_wanted_field == 'AGE'):
        _new_want_field = 3
    elif(_wanted_field == 'GENDER'):
        _new_want_field = 4
    else:
        _new_want_field = 5
    # for each username in the dictionary
    if(_new_want_field != 5 and _new_field != 5):
        for _username in _username_to_data:
            # check if the requirement is met
            if(_username_to_data[_username][_new_field] == _check_value):
                # if so, add the desired field of the user to the set
                selected.add(_username_to_data[_username][_new_want_field])
        # return the set
        return selected
    else:
        return set()
