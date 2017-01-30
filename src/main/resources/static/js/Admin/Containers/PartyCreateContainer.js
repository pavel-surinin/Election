var fileErrorMesages = [];
var nameErrorMesages = [];
var numberErrorMesages = [];

function validate(self, code){
  if (code == 415) {fileErrorMesages.push('Netinkamas CSV failas');}
  if (code == 417) {numberErrorMesages.push('Partija su tokiu numeriu jau užregistruota');}
  if (code == 418) {nameErrorMesages.push('Partija su tokiu pavadinimu jau užregistruota');}
  self.setState({
    fileErrorMesages : fileErrorMesages,
    numberErrorMesages :numberErrorMesages,
    nameErrorMesages :nameErrorMesages,
  });
}
function filePost(obj, data, header) {
  axios
  .post('/party', data, header)
  .then(function(response){
    obj.setState({postErrCode : response.status});
    obj.context.router.push('/admin/party?succesCreateText=Partija ' + obj.state.name + ' sukurta');
  })
  .catch(function (error) {
    if (error.response) {
      console.error(error.response.data.message);
      obj.setState({postErrCode : error.response.status});
      // console.log(error.response.headers);
    } else {
      console.error('Error', error.message);
    }
    // console.log(error.config);
  });
}

var PartyCreateContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      number : '',
      file : null,
      nameErrorMesages : [],
      numberErrorMesages : [],
      fileErrorMesages : [],
      postErrCode : 199,
    };
  },
  componentWillMount: function() {

  },
  onHandleFileChange : function(file){
    this.setState({file : file});
  },
  onHandleNumberChange : function(event){
    this.setState({number : event.target.value});
  },
  onHandleNameChange : function(event){
    this.setState({name : event.target.value});
  },
  onHandleSubmit : function(event){
    nameErrorMesages = [],
    numberErrorMesages = [],
    fileErrorMesages = [],
    event.preventDefault();
    var self = this;
    //party info constructing data
    var number = null;
    if (this.state.number != '') {
      number = parseInt(this.state.number);
    } else {
      number = null;
    }
    //uploadfile constructing data
    var file = 'nofile.aaa';
    if (this.state.file != null) {
      file = this.state.file;}
    var data = new FormData();
    var header = { headers: {
      'Content-Type': 'multipart/form-data',
      'Party-name': this.state.name.trim(),
      'Party-number': number} };
    data.append('file', file);
    var partyInfo = {
      name: this.state.name.trim(),
      partyNumber: number,
    };
    //validation
    if(!validation.checkForCsv(file.name)) {fileErrorMesages.push('Būtina prisegti *.csv formato failą');}
    if(!validation.checkPartyName(this.state.name)) {nameErrorMesages.push('Pavadinimą gali sudaryti tik raidės ir tarpai');}
    if(!validation.checkMax(this.state.name,50)) {nameErrorMesages.push('Pavadinimas negali būti ilgesnis, nei 50 simbolių');}
    if(!validation.checkMin(this.state.name,4)) {nameErrorMesages.push('Pavadinimas negali būti trumpesnis, nei 4 simboliai');}
    if(!validation.checkNumber(number)) {numberErrorMesages.push('Partijos numerio laukas priima tik skačius arba lieka tuščias');}
    if (nameErrorMesages.length == 0 &&
        fileErrorMesages.length == 0 &&
        numberErrorMesages.length == 0
      ) {

      // Writing file to java
      axios
      .post('/party', data, header)
      .then(function(response){
        console.log(response.status);
        self.context.router.push('/admin/party?succesCreateText=Partija ' + self.state.name + ' sukurta');
      })
      .catch(function (error) {
        if (error.response) {
          console.error(error.response.data.message);
          console.log(error.response.status);
          validate(self, error.response.status);
        }
      });
    } else {
      self.setState({
        nameErrorMesages : nameErrorMesages,
        numberErrorMesages : numberErrorMesages,
        fileErrorMesages : fileErrorMesages,
      });
    }
  },
  render: function() {
    return (
      <PartyCreateEditFormComponent
       onHandleNameChange={this.onHandleNameChange}
       onHandleNumberChange={this.onHandleNumberChange}
       onHandleFileChange={this.onHandleFileChange}
       onHandleSubmit={this.onHandleSubmit}
       name={this.state.name}
       number={this.state.number}
       nameErrorMesages={this.state.nameErrorMesages}
       numberErrorMesages={this.state.numberErrorMesages}
       fileErrorMesages={this.state.fileErrorMesages}
       action='Sukurti'
       />
    );
  }
});

PartyCreateContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.PartyCreateContainer = PartyCreateContainer ;
