
typedef double floatingPoint;
typedef i16 short;
typedef binary notText;
typedef i32 ThirtyTwo;
typedef i64 jlong;

const double myConstant = 3.14;
const map<string,i32> myMap = {"str1" : 8, "str_2" : -15, "str 3" : 2147483640};
const map<map<string,bool>,double> complexMap = {{"testStr" : 1} : myConstant, {"false String" : false} : 5};
const set<ThirtyTwo> intSet = [32, 2, 4, 8, 16];
const list<set<i32>> insaneList = [[3, 6], intSet, [15, 7, 5, 6]];
const simpleStruct structConst = {"integerThing" : 85};
const complexStruct insaneStruct = {"nestedStruct" : {"longThing" : 5000, "simpleThing" : structConst}, "anotherSimpleStruct" : {"integerThing" : 6670}};

/**
 * Example enum
 */
enum myEnum {
	sponge,
	seagull = 2,
	patrick = 3,
	squid,
}

struct simpleStruct {
	17: i32 integerThing;
}

struct complexStruct {
	99: myStruct nestedStruct,
	78: simpleStruct anotherSimpleStruct;
}

/**
 * Example structure
 * Multi-line comment
 No asterisk on this one!

 */
struct myStruct {
	5: map<simpleStruct, list<jlong>> mstrstrThing,
	12: jlong longThing,
	1:  optional bool booleanThing,
	10: list<set<list<ThirtyTwo>>> my_integerSet_list,
	11: myEnum menumThing,
	13: notText binThing,
	14: simpleStruct simpleThing ,
	15: list<simpleStruct> strucList,
	16: set<bool> simpleSet,
	25: list<binary> binList;
}

exception myExc {
	1: myStruct nestedStructure,
	2: binary bitsAndBytes,
	3: myEnum nestedEnumeration
}

/**
 * Example union
 */
union myOnion {
	2: myStruct structureThing,
	5: map<simpleStruct, list<jlong>> mstrstrThing,
	12: jlong longThing,
	1:  optional bool booleanThing,
	10: list<set<list<ThirtyTwo>>> my_integerSet_list,
	11: myEnum menumThing,
	13: notText binThing,
	14: simpleStruct simpleThing ,
	15: list<simpleStruct> strucList,
	16: set<bool> simpleSet
}

/**
 * Example service
 */
service Calculator {
	/** ** *boo!
	 * -- Example function *
	star */
	binary myFunc (1: i16 num1, 2: map<string,i32> entries, 3: set<string> names, 4: list<bool> truth);
	oneway void inputOnly(1: i32 firstOne);
	void blankFunc();
	i32 standardFunc(1: double input1, 2: i16 input2);
	string funcWithExceptions(1: i64 bigNum) throws (5: myExc error);
}
