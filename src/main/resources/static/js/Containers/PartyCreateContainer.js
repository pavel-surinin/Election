var PartyCreateContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      nameClone : false,
      nameErrorMesages : [],
      number : 0,
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
    var self = this;
<<<<<<< HEAD
    event.preventDefault();
    var partyName = {name: this.state.name.trim()};
    var successAlertText = ('Partija sukurta');
    axios
      var nameErrorMesages = [];
      if(!validation.checkPartyName(this.state.name)) {nameErrorMesages.push('Pavadinimą gali sudaryti tik raidės ir tarpai');}
      if(!validation.checkMax(this.state.name,50)) {nameErrorMesages.push('Pavadinimas negali būti ilgesnis, nei 50 simbolių');}
      if(!validation.checkMin(this.state.name,4)) {nameErrorMesages.push('Pavadinimas negali būti trumpesnis, nei 4 simboliai');}
      if (nameErrorMesages.length == 0) {
      var partyInfo = {
        name: this.state.name.trim(),
        partyNumber: this.state.number.trim(),
      };
      axios
      .post('/party', partyInfo)
      .then(function(response){
        console.log(response);
        self.context.router.push('/admin/party');
      })
      .catch(function(err){
        console.error('PartyCreateContainer.onHandleSubmit.axios', err);
        self.setState({nameClone : true});
      });
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
       />
    );
  }
});
PartyCreateContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.PartyCreateContainer = PartyCreateContainer ;
