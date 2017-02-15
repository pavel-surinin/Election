var fileErrorMesages = [];
var nameErrorMesages = [];
var numberErrorMesages = [];

function validate(self, code){
  if (code == 415) {fileErrorMesages.push('Netinkamas CSV failas');}
  if (code == 417) {numberErrorMesages.push('Partija su tokiu numeriu jau užregistruota');}
  if (code == 418) {nameErrorMesages.push('Partija su tokiu pavadinimu jau užregistruota');}
  if (code == 422) {fileErrorMesages.push('Blogi CSV duomenys');}
  self.setState({
    fileErrorMesages : fileErrorMesages,
    numberErrorMesages :numberErrorMesages,
    nameErrorMesages :nameErrorMesages,
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
      members : [],
    };
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
      'Content-Type': 'multipart/form-data'}};
    data.append('file', file);
    data.append('name', this.state.name.trim());
    data.append('number', number);
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
        self.context.router.push('/admin/party?succesCreateText=' + self.state.name + ' sukurta');
      })
      .catch(function (error) {
        if (error.response) {
          console.error(error.response.data.message);
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
       members={this.state.members}
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
