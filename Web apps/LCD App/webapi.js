/**
 * @brief Web server API
 */
const WebServerProtocol = "https://";

/**
 * @brief Web server API
 */
const WebServerAPI = {
  LcdWrite:          
    { url: "/lcd_write.php?",    args: ["line1", "line2"] },
  LcdRead:           
    { url: "/lcd_read.php?",     args: []                 },
  LcdService: 
    { url: "/lcd_service.php?",  args: ["enable"]         },
  LcdNext:
    { url: "/lcd_next.php?",     args: []                 },
};

/**
 * @brief Web server API request method
 * @param[in] resource : Selected web server resource
 * @param[in] args : Web server script input arguments 
 * @param[in] onResponse : Event handler on successful response 
 */
function WebServerRequest(resource, args, onResponse) {
  // Selected web resource 
  var url = WebServerProtocol + window.location.hostname + resource.url;
  // Add input arguments 
  args.forEach( (arg,i) => { 
    url += resource.args[i]+"="+arg+"&" 
  }); 
  // Send request
  $.ajax(url, { 
    type: 'GET', 
    dataType: 'text', 
    error: requestError,
    success: onResponse
  });
}