# Albion Ka Hei Fung
# Nov 24, 2015
# v 0.0.1
from reading import *
from database import *

# Below, write:
# *The cartesian_product function
# *All other functions and helper functions
# *Main code that obtains queries from the keyboard,
#  processes them, and uses the below function to output csv results


def print_csv(table):
    '''(Table) -> NoneType
    Print a representation of table.
    '''
    dict_rep = table.get_dict()
    columns = list(dict_rep.keys())
    print(','.join(columns))
    rows = int(table)
    for i in range(rows):
        cur_column = []
        for column in columns:
            cur_column.append(dict_rep[column][i])
        print(','.join(cur_column))

def run_query(database, query):
    '''(Databse, str) -> Table
    '''
    n_query = query.split()
    tables = n_query[3]
    tables = tables.split(',')
    r_table = final_table(database, tables)
    if(len(n_query) > 4):
        where = n_query[5]
        r_table = apply_condition(r_table, where)
    select = n_query[1].split(',')
    selected_ones = select_columns(r_table, select)
    return selected_ones

def select_columns(table, select):
    '''(Table, str) -> Table
    Given the table, selects specific headers
    '''
    # if the user wants everything, give em everything
    if select[0] == '*':
        return table
    # otherwise, give em what they want
    else:
        # initialize new table
        r_table = Table()
        # get old headers
        headers = table.get_headers()
        # initialize new headers
        r_header = []
        # get height of table
        t_length = int(table)
        # for each header selected
        for header in headers:
            if header in select:
                # add it to resulting (new) headers
                r_header += [header]
        # join the new headers
        new_header = ','.join(r_header)
        # and add it into the new table
        r_table.add_header(new_header)
        # then add the items in those headers to corresponding same
        # header in the new table
        for header in r_header:
            for i in range(t_length):
                r_item = table.get_item(i, header)
                r_table.add_item(header, r_item)
        # return the new item
        return r_table

def apply_condition(table, where):
    '''(Table, str) -> Table
    Applies the given where conditions to the given table. Returns the table
    after the where conditions are executed.
    REQ: where be in proper syntax:
    value1=value2 or value1>value2
    value1 and value2 can be headers to reference items in table
    REQ: table must not be empty
    >>>t = Table()
    >>>t.set_dict({'a.a': [1,2,3], 'a.b': [1,3,3]}
    >>>y = apply_condition(t, 'a.a=a.b')
    >>>y.get_dict()
    >>>{'a.a': [1,3], 'a.b': [1,3]}
    '''
    # split the where clause into individual constraints
    where = where.split(',')
    # initialize a table
    r_table = Table()
    # get the headers from the table
    headers = table.get_headers()
    # join them as a string
    headers = ','.join(headers)
    # so we can add headers to the new table
    r_table.add_header(headers)
    # for each where clause
    for clause in where:
        # identify if it's '=' or '>'
        if('=' in clause):
        # split at '=' and identify it as an == comparison
            condition = clause.split('=')
            equal_comp = True
        #split at '>' and identify it as an > comparison
        else:
            condition = clause.split('>')
            equal_comp = False
        # for each row in the table
        for i in range(int(table)):
            # make a copy of the original condition so we don't mess it up
            r_cond = condition[:]
            # set the values of each condition to that in the table's
            # if the condition is not a header name, then it retains the
            # string
            r_cond[0] = table.get_item(i, condition[0])
            # same for second condition
            r_cond[1] = fix_cond(table, condition[1], i)
            # compare the 2 values as requested,
            # adds the row to the result table if the condition is true
            r_table = compare_cond(r_table, table, i, r_cond, equal_comp)
            # rinse and repeat for all rows
    # return the table
    return r_table

def fix_cond(table, condition, i):
    '''(Table, str) -> str
    Tries to get a valid value for condition[i]. If it is a reference to
    '''
    # try and see if there this is a key
    try:
        # if it is, take the value of comparison
        r_cond = table.get_item(i, condition)
    except KeyError:
        # if it isn't a key, retain the original string
        if(condition[0] == "'" and condition[-1] == "'"):
            r_cond = condition[1:-1]
        # crash prevention
        else:
            r_cond = condition
    return r_cond

def compare_cond(r_table, table, row, condition, comp_type):
    '''(Table, Table, int, list of str, boolean) -> Table
    Given a Table table, we compare the condition[0] and condition[1].
    If it is true, we add the row in table to r_table. Returns r_table.
    REQ: r_table have the same headers as table
    REQ: table not be empty
    REQ: condition[0] can be compared to condition[1]
    REQ: don't enter a null value for comp_type. True means it's comparing
    condition[0] == condition[1], False means it's comparing condition[0] >
    condition[1]
    '''
    # get the headers in r_table. This should be the same headers as table's
    # as per REQ.
    headers = r_table.get_headers()
    # if we comparing ==
    if(comp_type):
        # compare the condition
        if(condition[0] == condition[1]):
            # if it's true, add the row from table to r_table
            for header in headers:
                r_table.add_item(header, table.get_item(row, header))
    # if we comparing >
    else:
        # compare the condition
        if(condition[0] > condition[1]):
            # if it's true, add the row from table to r_table
            for header in headers:
                r_table.add_item(header, table.get_item(row, header))
    # return r_table
    return r_table

def final_table(database, tables):
    '''(Database, list of str) -> Table
    Takes the part of the query after from and before where. Creates a new
    table, which would be the cartesian products of the tables requested.
    The table is returned.
    REQ: database must not be empty
    REQ: tables must be a string of table names that exists within database,
    in format: table1,table2,table3
    >>>t = Table()
    >>>t.set_dict({'a.a': [1, 2], 'a.b': [3, 4])
    >>>y = Table()
    >>>y.set_dict({'b.a': ['A', 'B'], 'b.b': ['C', 'D']})
    >>>z = Database()
    >>>z.set_dict('t': t, 'y': y)
    >>>request = 't,y'
    >>>r_table = final_table(z, request)
    >>>r_table.get_dict()
    {'a.a': [1,1,2,2], 'a.b': [3,3,4,4], 'b.a': ['A', 'B', 'A', 'B'],
     'b.b': ['C', 'D', 'C', 'D']}
    '''
    # if the user is not selecting all tables
    if(tables[0] != '*'):
        # if user selects 1 table
        if(len(tables) == 1):
            # set resulting table to the table requested
            r_table = database.get_table(tables[0])
        # if user requests more than 1 table
        elif(len(tables) > 1):
            # join all the tables requested as a cartesian product
            r_table = table_join(database, tables)
    # if all the tables are required
    elif(tables[0] == '*'):
        # get all the table names
        table_names = database.get_names()
        # join them all into a cartesian table
        r_table = table_join(databse, table_names)
    # this else is here for crash prevention
    else:
        # if syntax makes no sense, it returns an empty table
        r_table = Table()
    # return the resulting table
    return r_table

def table_join(database, tables):
    '''(Database, list str) -> Table
    Joins the table into a cartesian table.
    REQ: database not be empty
    REQ: list of strings are names of tables, and the tables with those names
    exists within database
    >>>t = Table()
    >>>t.set_dict({'a.a': [1, 2], 'a.b': [3, 4])
    >>>y = Table()
    >>>y.set_dict({'b.a': ['A', 'B'], 'b.b': ['C', 'D']})
    >>>z = Database()
    >>>z.set_dict('t': t, 'y': y)
    >>>request = ['t', 'y']
    >>>r_table = table_join(z, request)
    >>>r_table.get_dict()
    {'a.a': [1,1,2,2], 'a.b': [3,3,4,4], 'b.a': ['A', 'B', 'A', 'B'],
     'b.b': ['C', 'D', 'C', 'D']}
    '''
    # set the first table to r_table
    r_table = database.get_table(tables[0])
    # for all the tables requested
    for i in range(1, len(tables)):
        # get the next table and get cartesian product
        table_two = database.get_table(tables[i])
        r_table = cartesian_product(r_table, table_two)
        # rinse and repeat until all are joined
    # return the table
    return r_table

def cartesian_product(table_one, table_two):
    '''(Table, Table) -> Table
    Given 2 tables, returns the cartesian_product of the 2 tables as a Table.
    REQ: table_one and table_two not be empty tables
    >>>t = Table()
    >>>t.set_dict({'a.a': [1, 2], 'a.b': [3, 4])
    >>>y = Table()
    >>>y.set_dict({'b.a': ['A', 'B'], 'b.b': ['C', 'D']})
    >>>request = ['t', 'y']
    >>>r_table = cartesian_product(t, y)
    >>>r_table.get_dict()
    {'a.a': [1,1,2,2], 'a.b': [3,3,4,4], 'b.a': ['A', 'B', 'A', 'B'],
     'b.b': ['C', 'D', 'C', 'D']}
    '''
    # get the headers of table 1
    header_one = table_one.get_headers()
    # get table 1's length
    h_one_len = len(header_one)
    # get headers of table 2
    header_two = table_two.get_headers()
    # get table 2's length
    h_two_len = len(header_two)
    # get all the headers for assignment later
    headers = header_one + header_two
    # make that header a string
    headers = ','.join(headers)
    # get height of each table
    h_one_height = int(table_one)
    h_two_height = int(table_two)
    # initialize a new table
    r_table = Table()
    # set up the keys in the new table
    r_table.add_header(headers)
    # start from row 1
    # for each row in the first table
    for i in range(h_one_height):
    # add each of the second table's data to the row for each row
        for j in range(h_two_height):
            #for each hearder in table one
            r_table.add_row_table(table_one, header_one, i)
            #for header in header_one:
                # add it's object
                #r_table.add_item(header, table_one.get_item(i, header))
            # then for each header in table two
            r_table.add_row_table(table_two, header_two, j)
            #for headr in header_two:
                # add it's object
                #r_table.add_item(headr, table_two.get_item(j, headr))
    return r_table

def fix_dup(table):
    '''(Table) -> Table
    Returns a table that removes all duplicate rows from table.
    >>>t = Table()
    >>>t.set_dict({'a': ['hi', 'hi']})
    >>>fix_dup(t)
    {'a': ['hi']}
    '''
    # get height of table
    height = int(table)
    # if there's only 1 row, there can't be dup roles
    if(height <= 1):
        # since it's only got 1 row, just return that table
        r_table = table
        return r_table
    # get the headers
    headers = table.get_headers()
    # initialize a new table
    r_table = Table()
    # joining the headers so we add the headers to new table
    r_header = ','.join(headers)
    r_table.add_header(r_header)
    # assign the first header
    first_head = headers[0]
    # for each row of the table, we check it against the entire table that
    # was not checked against yet
    for row_check in range(height-1):
        # assume we want to add it (so we can use while loop)
        dont_add = False
        # the first item is always the same
        item_one = table.get_item(row_check, first_head)
        # check this item against the rest of the data below it
        for row in range(row_check+1, height-1):
            # get the next item to check (below it)
            item_two = table.get_item(row, first_head)
            # if they are the same, we check the next header's items of the
            # 2 specific rows
            if(item_one == item_two):
                # since the 2 items are the same, we will assume it's a dup
                # for now, so we can check in while loop
                dont_add = True
                # we don't need to check the first header since we already did
                # in the if statement
                i = 1
                # until we find 2 items that aren't the same in the 2 rows
                while(dont_add and i < len(headers)):
                    # we keep looking through the next headers
                    item_one_diff = table.get_item(row_check, headers[i])
                    item_two = table.get_item(row, headers[i])
                    # if those 2 items are the same, we keep looping
                    # and looking
                    if(item_one_diff == item_two):
                        i += 1
                    # otherwise, we've found a different element between
                    # the 2 rows, which means the rows aren't dup.
                    else:
                        # breaks the loop, checks the next row if there are
                        # still rows left after
                        dont_add = False
        # if we want to add the row (which means it got through the
        # checks are the row is not a dup of any other row)
        if(not dont_add):
            # add the row into the new table
            r_table.add_row_table(table, headers, row_check)
    # the last row is never a dup since all the other rows that are the
    # same as this one will not be added. So add last row
    r_table.add_row_table(table, headers, height-1)
    # return the table
    return r_table

if(__name__ == "__main__"):
    # read the database
    _database = read_database()
    # initialize the string to something so it the while loop runs
    query = 'kaboom'
    # while the user hasn't entred an empty line
    while(query != ''):
        # ask for query input
        query = input("Enter a SQuEaL query, or a blank line to exit:")
        if(not (query == '' or query == 'kaboom')):
            # run query
            r_table = run_query(_database, query)
            no_dup_r_table = fix_dup(r_table)
            print_csv(no_dup_r_table)
