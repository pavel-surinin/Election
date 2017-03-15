var fileErrorMesages = [];
var nameErrorMesages = [];
var numberErrorMesages = [];

function validate(self, code){
  if (code == 415) {fileErrorMesages.push('Netinkamas CSV failas');}
  if (code == 417) {numberErrorMesages.push('Partija su tokiu numeriu jau užregistruota');}
  if (code == 418) {nameErrorMesages.push('Partija su tokiu pavadinimu jau užregistruota');}
  if (code == 422) {fileErrorMesages.push('Blogi CSV duomenys');}
  if (code == 500) {numberErrorMesages.push('Pavadinimas arba Partijos numeris jau yra panaudoti');}
  self.setState({
    fileErrorMesages : fileErrorMesages,
    numberErrorMesages :numberErrorMesages,
    nameErrorMesages :nameErrorMesages,
    numberOfMembers : 0
  });
}
function getPartyById(self,id) {
  axios
  .get('/party/' + id)
  .then(function(response){
    console.log(response);
    EventEmitter.publish({ eventType: 'LogCounty' });
    self.setState({
      name : response.data.name.trim(),
      number : response.data.partyNumber,
      members : response.data.members,
      isLoading : false,
    });
  })
  .catch(function(err){
    console.error('PartyEditContainer.'+func+'.axios.get.party/:id', err);
  });
}

var PartyEditContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      number : '',
      file : null,
      members : [],
      nameErrorMesages : [],
      numberErrorMesages : [],
      fileErrorMesages : [],
      postErrCode : 199,
      deletedPartyFileText : '',
    };
  },
  submitWithFile : function(){
    nameErrorMesages = [];
    numberErrorMesages = [];
    fileErrorMesages = [];
    var self = this;
    //party info constructing data
    var pid = parseInt(this.props.params.id);
    //uploadfile constructing data
    var file = 'nofile.aaa';
    if (this.state.file != null) {
      file = this.state.file;}
    var data = new FormData();
    var header = { headers: {
      'Content-Type': 'multipart/form-data'}};
    data.append('file', file);
    data.append('name', this.state.name.trim());
    data.append('number', parseInt(this.state.number));
    data.append('id', pid);
    //validation
    if(!validation.checkForCsv(file.name)) {fileErrorMesages.push('Būtina prisegti *.csv formato failą');}
    if(!validation.checkPartyName(this.state.name)) {nameErrorMesages.push('Pavadinimą gali sudaryti tik raidės ir tarpai');}
    if(!validation.checkMax(this.state.name,50)) {nameErrorMesages.push('Pavadinimas negali būti ilgesnis, nei 50 simbolių');}
    if(!validation.checkMin(this.state.name,4)) {nameErrorMesages.push('Pavadinimas negali būti trumpesnis, nei 4 simboliai');}
    if(!validation.checkNumber(this.state.number)) {numberErrorMesages.push('Partijos numerio laukas priima tik skačius arba lieka tuščias');}
    if (nameErrorMesages.length == 0 &&
        fileErrorMesages.length == 0 &&
        numberErrorMesages.length == 0
      ) {
      // Writing file to java
      axios
      .post('/party', data, header)
      .then(function(response){
        self.context.router.push('/admin/party?succesCreateText=Partija ' + self.state.name + ' atnaujinta');
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
  submitWithoutFile : function(){
    nameErrorMesages = [];
    numberErrorMesages = [];
    fileErrorMesages = [];
    var self = this;
    var members = [];
    this.state.members.map(function(c){
      members.push({'id':c.id});
    });
    var putPartyInfo = {
      id : this.props.params.id,
      name : this.state.name.trim(),
      members : this.state.members,
      partyNumber : parseInt(this.state.number),
    };
    if(!validation.checkPartyName(this.state.name)) {nameErrorMesages.push('Pavadinimą gali sudaryti tik raidės ir tarpai');}
    if(!validation.checkMax(this.state.name,50)) {nameErrorMesages.push('Pavadinimas negali būti ilgesnis, nei 50 simbolių');}
    if(!validation.checkMin(this.state.name,4)) {nameErrorMesages.push('Pavadinimas negali būti trumpesnis, nei 4 simboliai');}
    if(!validation.checkNumber(this.state.number)) {numberErrorMesages.push('Partijos numerio laukas priima tik skačius arba lieka tuščias');}
    if (nameErrorMesages.length == 0 &&
        fileErrorMesages.length == 0 &&
        numberErrorMesages.length == 0
      ) {
      axios
        .put('/party', putPartyInfo)
        .then(function(response){
          self.context.router.push('/admin/party?succesCreateText=Partija ' + self.state.name + ' atnaujinta');
        })
        .catch(function (error) {
          if (error.response) {
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
  componentWillMount: function() {
    getPartyById(this, this.props.params.id);
  },
  onHandleDeleteClick : function(event){
      var self = this;
      event.preventDefault();
      axios
        .delete('/candidate/party/'+ this.props.params.id)
        .then(function(response){
          self.setState({deletedPartyFileText: 'Partijos sąrašas ištrintas'})
          getPartyById(self, self.props.params.id);
        })
        .catch(function(err){
          console.error('PartyEditContainer.onHandleDeleteClick.axios', err);
        });
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
    if (this.state.file != null) {
      this.submitWithFile();
    } else {
      this.submitWithoutFile();
    }
  },
  render: function() {
    return (
      <PartyCreateEditFormComponent
       onHandleNameChange={this.onHandleNameChange}
       onHandleNumberChange={this.onHandleNumberChange}
       onHandleFileChange={this.onHandleFileChange}
       onHandleSubmit={this.onHandleSubmit}
       onHandleDeleteClick={this.onHandleDeleteClick}
       name={this.state.name}
       members={this.state.members}
       number={this.state.number}
       nameErrorMesages={this.state.nameErrorMesages}
       numberErrorMesages={this.state.numberErrorMesages}
       fileErrorMesages={this.state.fileErrorMesages}
       action='Atnaujinti'
       deletedPartyFileText={this.state.deletedPartyFileText}
       />
    );
  },
});


PartyEditContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.PartyEditContainer = PartyEditContainer;
