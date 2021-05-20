## Author: AW <AW@DESKTOP-S5FOS71>
## Created: 2020-05-09

function exportTestDatatoJS(input, refOutput, decimalPrecision, filename)
testvar = 'var MyFirTestData = {\n';
in = '\tInput: [\n';
out = '\tRefOutput:[\n';

for i = 1 : length(input)-1
  in  = [in  '\t\t' num2str(    input(i),decimalPrecision) ',\n'];
  out = [out '\t\t' num2str(refOutput(i),decimalPrecision) ',\n'];
endfor
  in =  [in '\t\t' num2str(    input(end),decimalPrecision) '\n\t],\n'];
  out = [out '\t\t' num2str(refOutput(end),decimalPrecision) '\n\t]\n}'];

filecontent = [testvar in out];

fileID = fopen(filename,'w');
fprintf(fileID,filecontent);
fclose(fileID);

endfunction
