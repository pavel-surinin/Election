var styles = {
  toggle :
  function(number){
    if (number == 0) {
      return {display : 'inherit'};
    } else {
      return {display : 'none'};
    }
  },
  toggleTF :
  function(bln){
    if (bln) {
      return {display : 'inherit'};
    } else {
      return {display : 'none'};
    }
  },
  toggleGreen :
  function(bool){
    if (bool) {
      return {background : '#dff0d8'};
    } else {
      return {background : 'none'};
    }
  },
  toggleResultNav :
  function(bool){
    if (bool != undefined) {
      return {display : 'inherit'};
    } else {
      return {display : 'none'};
    }
  },

};

window.styles = styles;
