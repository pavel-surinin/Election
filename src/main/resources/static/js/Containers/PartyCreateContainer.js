function filePost(obj, data, header) {
  axios
  .post('/upload/candidates', data, header)
  .then(function(response){
    console.log(response);
    self.context.router.push('/admin/party?succesCreateText=Partija ' + self.state.name + ' sukurta');
  })
  .catch(function(error){
    // console.error('PartyCreateContainer.onHandleSubmit.axios ', err);
    console.log(error.response.status);
    console.log(error.response.data);
  });
}

function partyPost(self, partyInfo){
  axios
  .post('/party', partyInfo)
  .then(function(response){
    self.context.router.push('/admin/party?succesCreateText=Partija ' + self.state.name + ' sukurta');
  })
  .catch(function(err){
    console.error('PartyCreateContainer.onHandleSubmit.axios', err);
    self.setState({nameClone : true});
  });

}

var PartyCreateContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      nameClone : false,
      number : '',
      file : null,
      nameErrorMesages : [],
      numberErrorMesages : [],
      fileErrorMesages : [],
    };
  },
  componentWillMount: function() {
    this.setState({nameClone : false});
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
    var fileErrorMesages = [];
    if(!validation.checkForCsv(file.name)) {fileErrorMesages.push('Būtina prisegti *.csv formato failą');}
    var nameErrorMesages = [];
    if(!validation.checkPartyName(this.state.name)) {nameErrorMesages.push('Pavadinimą gali sudaryti tik raidės ir tarpai');}
    if(!validation.checkMax(this.state.name,50)) {nameErrorMesages.push('Pavadinimas negali būti ilgesnis, nei 50 simbolių');}
    if(!validation.checkMin(this.state.name,4)) {nameErrorMesages.push('Pavadinimas negali būti trumpesnis, nei 4 simboliai');}
    var numberErrorMesages = [];
    if(!validation.checkNumber(number)) {numberErrorMesages.push('Partijos numerio laukas priima tik skačius arba lieka tuščias');}
    if (nameErrorMesages.length == 0 &&
        fileErrorMesages.length == 0 &&
        numberErrorMesages.length == 0
      ) {
      filePost(this, data, header);
      // partyPost(self, partyInfo);
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
       nameClone={this.state.nameClone}
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
