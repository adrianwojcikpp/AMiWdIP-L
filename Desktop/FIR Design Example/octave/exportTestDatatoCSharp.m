## Author: AW <AW@DESKTOP-S5FOS71>
## Created: 2020-05-09

function exportTestDatatoCSharp(input, refOutput, decimalPrecision, namespace, filename)
  
  ns = ['namespace ' namespace '\n{\n'];
  class = '\tpublic static class MyFirTestData {\n';
  in  = '\t\tpublic static readonly double[] Input = {\n';
  out = '\t\tpublic static readonly double[] RefOutput = {\n';
   
  for i = 1 : length(input)-1
    in  = [in  '\t\t\t' num2str(    input(i),decimalPrecision) ',\n'];
    out = [out '\t\t\t' num2str(refOutput(i),decimalPrecision) ',\n'];
  endfor
    in =  [in  '\t\t\t' num2str(    input(end),decimalPrecision) '\n\t\t};\n'];
    out = [out '\t\t\t' num2str(refOutput(end),decimalPrecision) '\n\t\t};\n\t}\n}'];
    
  filecontent = [ns class in out];

  fileID = fopen(filename,'w');
  fprintf(fileID,filecontent);
  fclose(fileID);
  
endfunction
