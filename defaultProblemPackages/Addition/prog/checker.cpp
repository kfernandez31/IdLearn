#include <iostream>
#include "../../packageUtils.h"

using namespace std;
using namespace pUtils;

int main(int argc, char * * argv) {
	if (argc != 4) {
		cerr << "Usage: " << argv[0] << " <input> <user output> <model output>\n";
		return 1;
	}

	Reader uOutput(argv[2]);
	Reader mOutput(argv[3]);

	bool result = true;

	try {
		result &= uOutput.read<si>() == mOutput.read<si>();
		result &= uOutput.eof();
	} catch (...) {
		cout << "WRONG\n";
		return 1;
	}

	if (result) {
		cout << "OK\n";
		return 0;
	} else {
		cout << "WRONG\n";
		return 1;
	}
}