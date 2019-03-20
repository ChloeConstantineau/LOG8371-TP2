var request = require("request");

const url = process.argv[4];
const numberRequests = process.argv[3];
const scenarioNumber = process.argv[2]
var fs = require("fs");

const pathToScenario = ".\\data\\dataset" + scenarioNumber +".arff";

var options = {
  method: 'POST',
  url:  url + "/algorithm/NaiveBayes",
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
	console.log(url);
for (let i = 0; i < numberRequests; i++) {

  request(options, function (error, response, body) {
    if (error) throw new Error(error);
  
    console.log(body);
  });
}

