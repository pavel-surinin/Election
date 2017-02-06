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
  }


};

window.styles = styles;
