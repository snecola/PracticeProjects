/**
 * A trigger-driven function that adds the trainee to the Trainee Checklist
 * Spreadsheet and creates the Trainee's progression document when a Recruitment
 * Officer enters a new Trainee through the Trainee Training Document Creation Form.
 *
 * @param {Object} e The event parameter for form submission to a spreadsheet;
 *     see https://developers.google.com/apps-script/understanding_events
 */
function onFormSubmit(e) {
  
  /*
   * Document the IDs for the pre-existing files
   */
  const traineeChecklistSpreadsheetID = "1dXdcoZKB59SXHlgYSohORjJB6SL7swq-Bg5zJE9CZYM";
  const traineeFolderID = "19f9AlsU1YAQ6T3zXjAYs5F-u01Tqv3F0";
  const traineeDocumentTemplateID = "12wDtRPlHuor1kX54R-buEu9R6x2dg_eyXzajNuc5eiY";

  /*
   * Get the Trainee's IGN from the submitted form
   */
  var traineeIGN = e.namedValues["Trainee's IGN?"][0];
  
  /*
   * Add the Trainee to the Trainee Checklist Spreadsheet by adding them to the All
   * Sheet and creating a new Sheet for them based off the Template Sheet
   */

  // Get the Trainee Checklist Spreadsheet
  var traineeChecklistSpreadsheet = SpreadsheetApp.openById(traineeChecklistSpreadsheetID);

  // Get the All Sheet from the Trainee Checklist Spreadsheet
  var traineeChecklistAllSheet = traineeChecklistSpreadsheet.getSheetByName("All");

  // Create a new Sheet in the Trainee Checklist Spreadsheet for the new Trainee based on the Template Sheet.
  var traineeChecklistTraineeSheet = traineeChecklistSpreadsheet.getSheetByName("Template").copyTo(traineeChecklistSpreadsheet);
  
  // Get the row number to add the Trainee to in the All Sheet
  var traineeChecklistAllSheetTraineeRowNum = 0;
  if (traineeChecklistAllSheet.getRange("A2").isBlank()) {
    traineeChecklistAllSheetTraineeRowNum = 2;
  } else {
    var traineeChecklistAllSheetTraineeCellAbove = traineeChecklistAllSheet.getRange("A1").getNextDataCell(SpreadsheetApp.Direction.DOWN).getA1Notation();
    traineeChecklistAllSheetTraineeRowNum = parseInt(traineeChecklistAllSheetTraineeCellAbove.substring(1, traineeChecklistAllSheetTraineeCellAbove.length)) + 1;
  }
  
  // Get the current date
  const today = new Date();
  const month = today.toLocaleString('default', { month: 'short' });
  
  // Add the Trainee to the All Sheet
  traineeChecklistAllSheet.getRange("A" + traineeChecklistAllSheetTraineeRowNum).setValue(traineeIGN);
  traineeChecklistAllSheet.getRange("B" + traineeChecklistAllSheetTraineeRowNum).setValue(today.getDate() + " " + month + " " + today.getFullYear());
  
  // Rename the Trainee's Sheet to the Trainee's IGN and input the Trainee's IGN into the Sheet.
  traineeChecklistTraineeSheet.setName(traineeIGN);
  traineeChecklistTraineeSheet.getRange("A2").setValue(traineeIGN);
  
  /*
   * Create Trainee Document based on the Document Template
   */

  // Make a copy of the Document Template and use the Trainee's IGN to name the newly created Document.
  var traineeFolder = DriveApp.getFolderById(traineeFolderID);
  var traineeDocumentTemplate = DriveApp.getFileById(traineeDocumentTemplateID);
  var traineeDocumentFile = traineeDocumentTemplate.makeCopy(traineeIGN, traineeFolder);

  // Open the Trainee's Document
  var traineeDocument = DocumentApp.openById(traineeDocumentFile.getId());

  // Set the Header in the document (1st Paragraph) to the Trainee's IGN
  traineeDocument.getBody().getParagraphs()[0].setText(traineeIGN);  
  
}