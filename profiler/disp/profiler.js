const config = (url) => {
	return {
		url: url + "/algorithm/NaiveBayes",
		1: {
			numberRequests: 10,
			file: "scenario_1\\dataset.arff"
		},
		2: {
			numberRequests: 10,
			file: "scenario_2\\dataset.arff"
		},
		3: {
			numberRequests: 10,
			file: "scenario_3\\dataset.arff"
		}, 
		4: {
			numberRequests: 10,
			file: "scenario_4\\dataset.arff"
		},
	}
}

var request = require("request");

const url = process.argv[3];
const scenarioNumber = process.argv[2];
const numberRequests = config[scenarioNumber];
var fs = require("fs");

const pathToScenario = ".\\scenarios\\scenario_" + scenarioNumber + "\\dataset.arff";

var options = {
  method: 'POST',
  url:  config(url),
  headers:
  {
    'Postman-Token': 'fc797e92-5f0b-4a3b-86d9-84a7b52a5a4f',
    'cache-control': 'no-cache',
    Connection: 'keep-alive',
    Accept: 'text/uri-list',
    'Content-Type': 'application/x-www-form-urlencoded,multipart/form-data'
  },
  formData:
  {
    batchSize: '100',
    useKernelEstimator: '0',
    useSupervisedDiscretization: '0',
    validation: 'CrossValidation',
    validationNum: '10',
    file:
    {
      value: fs.createReadStream(pathToScenario),
      options:
      {
        filename: pathToScenario,
        contentType: null
      }
    }
  }
};

for (let i = 0; i < numberRequests; i++) {
  request(options, function (error, response, body) {
    if (error) throw new Error(error);
  
    console.log(body);
  });
}

