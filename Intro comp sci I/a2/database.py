# Albion Ka Hei Fung
# Nov 25, 2015
# v 0.0.1


class Table():
    '''A class to represent a SQuEaL table'''
    def __init__(self):
        '''(Table) -> NoneType
        Initializing method. Creates a table and holds data in a table.
        '''
        # set up new empty code
        self._column_to_data = {}

    def __int__(self):
        '''(Table) -> int
        Returns the number of rows in the table. If no data has been set,
        it will return 0
        >>>t = Table()
        >>>int(t)
        0
        >>>t.add_dict({'a': [1,2,3])
        >>>int(t)
        3
        '''
        # get the number of rows and return it
        return self.get_row_num()

    def set_dict(self, new_dict):
        '''(Table, dict of {str: list of str}) -> NoneType

        Populate this table with the data in new_dict. Erases old data.
        The input dictionary must be of the form:
            column_name: list_of_values
        >>>t = Table()
        >>>t.set_dict({'a': [1,2,3]})
        >>>t.get_dict()
        {'a': [1,2,3]}
        >>>t.set_dict({'b': ['Bye old data']})
        >>>t.get_dict()
        {'b': ['Bye old data']}
        '''
        # rewrite original data
        self._column_to_data = {}
        # add the new data in
        for column in new_dict:
            self._column_to_data[column] = new_dict[column][:]

    def get_dict(self):
        '''(Table) -> dict of {str: list of str}

        Return the dictionary representation of this table. The dictionary keys
        will be the column names, and the list will contain the values
        for that column.
        >>>t = Table()
        >>>t.set_dict({'a': [1,2,3]})
        >>>t.get_dict()
        {'a': [1,2,3]}
        '''
        # return the data
        return self._column_to_data

    def add_header(self, header):
        '''(Table, str) -> NoneType
        Given the header of each column it creates a new column with the header
        as the header in the table.
        REQ: header be in this format:
        header0,header1,header2,header3
        etc
        where header0 is the header for column0, header1 is the header for
        column1, etc
        >>>t = Table()
        >>>t.add_header(['a.b,b.b'])
        >>>t.get_dict()
        {'a.b': [], 'b.b': []}
        '''
        # strip the line and add it to memory
        self._headers = header.strip()
        # split it at ','
        self._headers = self._headers.split(',')
        # for each header
        for new_header in self._headers:
            # make a new dictionary entry
            self._column_to_data[new_header] = []

    def add_row(self, new_line):
        '''Given the line of string, adds it to the current table of data as
        a new row.
        REQ: row data must be in this format:
        data0, data1, data2, data3
        etc
        where data0 belongs to column 0, data1 belongs to column 1, etc
        >>>t = Table()
        >>>t.add_header(['a.a,b.b'])
        >>>t.add_row(['1, 2']
        >>>t.get_dict()
        {'a.a': ['1'], 'b.b': ['2']}
        '''
        # strip the line
        new_line = new_line.strip()
        # split it at ','
        new_line = new_line.split(',')
        # new list for less confusion
        new_line_two = []
        for i in range(len(new_line)):
            new_line_two += [new_line[i].strip()]
        # for each data, add it to the respective column
        for i in range(len(self._headers)):
            self._column_to_data[self._headers[i]] += [new_line_two[i]]

    def add_row_table(self, table, headers, row):
        '''(Table, list of str, int) -> NoneType
        Adds the row'th number row from table to this table. This method is
        only available if self is a product of the new table.
        REQ: self already has all header in headers as header for some column
        >>>t = Table()
        >>>t.set_dict({'a': [1,2], 'b': [3,4], 'c': [5,6]})
        >>>y = Table()
        >>>y.add_row_table(t, ['a', 'b'], 1)
        >>>y.get_dict()
        {'a': [1], 'b': [2]}
        '''
        # for each header
        for header in headers:
            # get the item from old table, add it to self
            item = table.get_item(row, header)
            self.add_item(header, item)

    def get_item(self, num, header):
        '''(Table, int, str) -> str
        Returns the requested value in the table, wher num is the row number,
        header is the column name. First row (header) is row 0, add 1 for each
        row below.
        >>>t = Table()
        >>>t.set_dict({'a': [1,2]})
        >>>t.get_item(0, 'a')
        1
        '''
        # return the item
        return self._column_to_data[header][num]

    def add_item(self, header, item):
        '''(Table, str, obj) -> NoneType
        Given the object and the header, adds the obj to a new row in header's
        column.
        >>>t = Table()
        >>>t.get_dict()
        {}
        >>>t.add_item('a', 1)
        >>>t.get_dict()
        {'a': [1]}
        >>>t.add_item('a', 2)
        >>>t.get_dict()
        {'a': [1,2]}
        '''
        try:
            # add item in the specific column
            self._column_to_data[header] += [item]
        # if the header didn't exist
        except KeyError:
            # we make a new column and put the data in
            self._column_to_data[header] = [item]

    def get_headers(self):
        '''(Table) -> list of str
        Returns all the headers of each column in the table as a
        list of string.
        >>>t = Table()
        >>>t.get_headers()
        []
        >>>t.add_header('a,b')
        >>>t.get_headers()
        ['a', 'b']
        '''
        # initialize a list
        headers = []
        # for each key (which is header)
        for header in self._column_to_data:
            # add the header as a list
            headers += [header]
        # return all the headers
        return headers

    def get_row_num(self):
        '''(Table) -> int
        Returns the amount of rows in the table. Credits for assistence on
        how to do this goes to Vatamanu.
        >>>t = Table()
        >>>t.add_header('a,b')
        >>>t.add_row('a, c')
        >>>t.get_row_num()
        1
        '''
        if(len(self.get_headers()) > 0):
            # get a random key
            key = list(self._column_to_data.keys())[0]
            # see the length of the table
            count = len(self._column_to_data[key])
        else:
            count = 0
        # return the length
        return count


class Database():
    '''A class to represent a SQuEaL database'''
    def __init__(self):
        '''(Database) -> NoneType
        Creates a database that will hold tables.
        '''
        # declare initial table
        self._name_to_table = {}

    def add_table(self, name, new_table):
        '''(Database, str, table) -> NoneType
        Given the name of the table and the table itself, the database stores
        the table.
        >>>t = Table()
        >>>t.add_header('a.a,b.b')
        >>>t.add_row('a,b')
        >>>t.get_dict()
        {'a.a': 'a', 'b.b': 'b'}
        >>>d = Database()
        >>>d.add_table('eg', t)
        >>>d.get_dict()
        {'eg': <__main__.object Table at memory address of t>}
        '''
        # add the table
        self._name_to_table[name] = new_table

    def set_dict(self, new_dict):
        '''(Database, dict of {str: Table}) -> NoneType

        Populate this database with the data in new_dict. Erases old data.
        new_dict must have the format:
            table_name: table
        >>>d = Database()
        >>>t = Table()
        >>>d.set_dict({'eg': t})
        >>>d.get_dict()
        {'eg': <__main__.object Table at memory address of t>}
        '''
        # over write current database
        self._name_to_table = {}
        # for each table in the given dict
        for table_name in new_dict:
            # set the table to the name (key of dict)
            new_table = new_dict[table_name]
            self._name_to_table[table_name] = new_table

    def get_dict(self):
        '''(Database) -> dict of {str: Table}

        Return the dictionary representation of this database.
        The database keys will be the name of the table, and the value
        with be the table itself.
        '''
        # return the dict
        return self._name_to_table

    def get_table(self, name):
        '''(Database, str) -> Table
        Returns the table object requested
        REQ: name must exist as one of the table names
        >>>t = Table()
        >>>d = Database()
        >>>d.add_table('chicken nugget', t)
        >>>d.get_table('chicken nugget')
        <__main__.object Table at memory address of t>
        '''
        # return the table
        return self._name_to_table[name]

    def get_names(self):
        '''(Databse) -> list of str
        Returns a list of string that is all the column names
        >>>d = Databse()
        >>>d.get_names()
        []
        >>>t = Table()
        >>>d.add_table('table 1', t)
        >>>d.get_dict()
        {'table 1': <__main__.Table object at memory address of t}
        '''
        # return all the keys
        names = list(self._name_to_table.keys())
        return names
