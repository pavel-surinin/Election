var validation = {
  checkMin :
  function checkMin(name, l) {
    if (name.length  >= l) {
      return true;
    } else {
      return false;
    }
  },
  checkMax :
  function checkMax(name, l) {
    if (name.length  <= l) {
      return true;
    } else {
      return false;
    }
  },
  checkSurname :
  function checkSurname(name) {
    var doubleSurnameRegex = new RegExp('^[A-Za-zĄČĘĖĮŠŲŪŽąčęėįšųūž]*-[A-Za-zČĘĖĮŠŲŪŽąčęėįšųūž]*$');
    var surnameRegex = new RegExp('^[A-Za-zČĘĖĮŠŲŪŽąčęėįšųūž]*$');
    var isMatchingDouble = doubleSurnameRegex.test(name);
    var isMatching = surnameRegex.test(name);
    if (isMatchingDouble || isMatching) {
      return true;
    } else {
      return false;
    }
  },
  checkName :
  function checkName(name) {
    var surnameRegex = new RegExp('^[A-Za-zČĘĖĮŠŲŪŽąčęėįšųūž]*$');
    return surnameRegex.test(name);
  },
  checkPartyName :
  function checkPartyName(name) {
    var surnameRegex = new RegExp('^[ČĘĖĮŠŲŪŽąčęėįšųūžA-Za-z\\s]+$');
    return surnameRegex.test(name);
  },
  checkCountyName :
  function checkPartyName(name) {
    var surnameRegex = new RegExp('^[ČĘĖĮŠŲŪŽąčęėįšųūžA-Za-z-\\s]+$');
    return surnameRegex.test(name);
  },
  checkForCsv :
  function checkForCsv(name) {
    if (name == undefined) {
      return false;
    }
    var lastFour = name.substr(name.length - 4); // => "Tabs1"
    if (lastFour == '.csv') {
      return true;
    } else {
      return false;
    }
  },
  checkNumber :
  function checkNumber(number) {
    var num = Number.isInteger(number);
    if (!isNaN(number) || num || number == null) {
      return true;
    } else {
      return false;
    }
  },
  showMsg :
  function checkMin(obj,index) {
    if (obj !== undefined) {
      var arr = [];
      obj.forEach(function(entry) {
        arr.push(<div key={index} className='alert alert-danger'> <strong>Dėmėsio! </strong>{entry}</div>);
      });
      return arr;
    }
  }
};

window.validation = validation;
