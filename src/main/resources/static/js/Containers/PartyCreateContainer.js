
var PartyCreateContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      nameClone : false,
      nameErrorMesages : [],
      number : '',
      file : null,
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
    //uploadfile
    var file = this.state.file;
    var data = new FormData();
    var header = { headers: { 'Content-Type': 'multipart/form-data' } };
    data.append('file', file);
    axios
      .post('/upload/candidates', data, header)
      .then(function(response){
        console.log(response);
      })
      .catch(function(err){
        console.error('PartyCreateContainer.onHandleSubmit.axios ', err);
      });

    //validation
    var nameErrorMesages = [];
    if(!validation.checkPartyName(this.state.name)) {nameErrorMesages.push('Pavadinimą gali sudaryti tik raidės ir tarpai');}
    if(!validation.checkMax(this.state.name,50)) {nameErrorMesages.push('Pavadinimas negali būti ilgesnis, nei 50 simbolių');}
    if(!validation.checkMin(this.state.name,4)) {nameErrorMesages.push('Pavadinimas negali būti trumpesnis, nei 4 simboliai');}
    if (nameErrorMesages.length == 0) {
      var number = null;
      if (this.state.number != '') {
        number = parseInt(this.state.number);
      }
      var partyInfo = {
        name: this.state.name.trim(),
        partyNumber: number,
      };
      console.log(partyInfo);
      axios
      .post('/party', partyInfo)
      .then(function(response){
        self.context.router.push('/admin/party?succesCreateText=Partija ' + self.state.name + ' sukurta');
      })
      .catch(function(err){
        console.error('PartyCreateContainer.onHandleSubmit.axios', err);
        self.setState({nameClone : true});
      });
    } else {
      self.setState({nameErrorMesages : nameErrorMesages});
    }
  },
  render: function() {
    return (
      <PartyCreateEditFormComponent
       onHandleNameChange={this.onHandleNameChange}
       onHandleNumberChange={this.onHandleNumberChange}
       name={this.state.name}
       number={this.state.number}
       onHandleSubmit={this.onHandleSubmit}
       nameClone={this.state.nameClone}
       action='Sukurti'
       nameErrorMesages={this.state.nameErrorMesages}
       onHandleFileChange={this.onHandleFileChange}
       />
    );
  }
});

PartyCreateContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.PartyCreateContainer = PartyCreateContainer ;
