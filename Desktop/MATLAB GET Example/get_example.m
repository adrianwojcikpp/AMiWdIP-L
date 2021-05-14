%{
 ******************************************************************************
 * @file    MATLAB GET Example/get_example.m
 * @author  Adrian Wojcik
 * @version V1.2
 * @date    09-May-2020
 * @brief   Simple MATLAB web client example
 ******************************************************************************
%}

import matlab.net.*
import matlab.net.http.*
r = RequestMessage;
uri = URI('http://192.168.56.5/sine.php');

figure();
p = plot(nan,nan, 'r');

k = 0;

while 1
    resp = send(r,uri);
    status = resp.StatusCode;
    if status == 'OK'
        data = resp.Body.Data; % {"t":0,"s":0}
        x = data.t;
        y = data.s;
        updatePlot(p, x, y);
    end
    k = k + 1;
end

function updatePlot(obj, new_x, new_y)
    obj.XData = [obj.XData, new_x];
    obj.YData = [obj.YData, new_y];
    drawnow;
end