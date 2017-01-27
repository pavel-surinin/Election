
var PartyCreateContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      nameClone : false,
      nameErrorMesages : [],
      number : '',
    };
  },
  componentWillMount: function() {
    this.setState({nameClone : false});
  },

  onHandleNumberChange : function(event){
    this.setState({number : event.target.value});
  },
  onHandleNameChange : function(event){
    this.setState({name : event.target.value});
  },
  onHandleSubmit : function(event){
    event.preventDefault();
    console.log(this);
    var self = this;
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

       />
    );
  }
});
PartyCreateContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.PartyCreateContainer = PartyCreateContainer ;
