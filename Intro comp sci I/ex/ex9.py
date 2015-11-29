import math
class Rectangle():
    '''Creates a rectangle
    '''
    def __init__(self, base, side):
        '''(self, int, int) -> NoneType
        Initializing method.
        REQ: base, side > 0
        '''
        # define base and side
        self._base = base
        self._side = side
        # set up the angle and radians
        self.set_up_angle(90)
        # let the object discover itself.
        self._race = 'Rectangle'

    def __str__(self):
        '''(self) -> str
        Returns a string that describes it's type and area
        >>>s = Rectangle(5,7)
        >>>print(s)
        I am a Rectangle with area 35.0
        '''
        # get the area
        _area = str(self.area())
        # put together the string
        _string = 'I am a '
        _string += self._race
        _string += ' with area '
        _string += _area
        # return the string
        return _string

    def set_up_angle(self, theta):
        '''(self, int) -> NoneType
        Sets up the angles in degree and radian of the object
        '''
        # set up the angle
        self._angle = theta
        # set up radians
        self._rad = math.radians(self._angle)

    def area(self):
        '''(self) -> float
        Returns the object's area
        >>>s = Square(5)
        >>>s.area()
        25.0
        '''
        # get area from formula
        _area = self._base*self._side*math.sin(self._rad)
        # return area
        return _area

    def bst(self):
        '''(self) -> list of ints
        Returns a list of ints containing the object's base length, side length
        and angle in degrees
        >>>s = Square(5)
        >>>s.bst()
        [5,5,90]
        >>>s = Parallelogram(5,7,30)
        >>>s.bst()
        [5,7,30]
        '''
        # get the list
        _return_list = [self._base, self._side, self._angle]
        # return the list
        return _return_list


class Square(Rectangle):
    '''Creates a square object
    '''
    def __init__(self, base):
        '''(self, int) -> NoneType
        Initializing method.
        REQ: base > 0
        '''
        # use init of Retangle
        Rectangle.__init__(self, base, base)
        # fix being mis-identified as a rectangle
        self._race = 'Square'


class Rhombus(Square):
    '''Creates a rhombus object
    '''
    def __init__(self, base, theta):
        '''(self, int, int) -> NoneType
        Initializing method
        REQ: base, theta > 0, theta is in degrees
        '''
        # use square's init
        Square.__init__(self, base)
        # set up angles properly
        Rectangle.set_up_angle(self, theta)
        # fixed object's identity crisis
        self._race = 'Rhombus'


class Parallelogram(Rectangle):
    '''Creates a parallelogram
    '''
    def __init__(self, base, side, theta):
        '''(self, int, int, int) -> NoneType
        Initializing method.
        '''
        # use rectangle's init
        Rectangle.__init__(self, base, side)
        # fix the angles to proper values
        Rectangle.set_up_angle(self,theta)
        # fixed another object's identity crisis
        self._race = 'Parallelogram'