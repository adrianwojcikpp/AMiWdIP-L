## Author: AW <AW@DESKTOP-S5FOS71>
## Created: 2020-05-09

function retval = exportTestDatatoJava(input, refOutput, decimalPrecision, package, filename)

  pk = ['package ' package ';\n\n'];
  class = 'public final class MyFirTestData {\n';
  in  = '\tpublic static final double[] Input = {\n';
  out = '\tpublic static final double[] RefOutput = {\n';
   
  for i = 1 : length(input)-1
    in  = [in  '\t\t' num2str(    input(i),decimalPrecision) ',\n'];
    out = [out '\t\t' num2str(refOutput(i),decimalPrecision) ',\n'];
  endfor
    in =  [in  '\t\t' num2str(    input(end),decimalPrecision) '\n\t};\n'];
    out = [out '\t\t' num2str(refOutput(end),decimalPrecision) '\n\t};\n}'];
    
  filecontent = [pk class in out];

  fileID = fopen(filename,'w');
  fprintf(fileID,filecontent);
  fclose(fileID);

endfunction
