
var PartyCreateContainer = React.createClass({
  getInitialState: function() {
    return {
      name : '',
      nameClone : false,
      nameErrorMesages : [],
    };
  },
  componentWillMount: function() {
    this.setState({nameClone : false});
  },
  onHandleNameChange : function(event){
    this.setState({name : event.target.value});
  },
  onHandleSubmit : function(event){
    event.preventDefault();
    var self = this;
    //validation
    var nameErrorMesages = [];
    if(!validation.checkPartyName(this.state.name)) {nameErrorMesages.push('Pavadinimą gali sudaryti tik raidės ir tarpai');}
    if(!validation.checkMax(this.state.name,50)) {nameErrorMesages.push('Pavadinimas negali būti ilgesnis, nei 50 simbolių');}
    if(!validation.checkMin(this.state.name,4)) {nameErrorMesages.push('Pavadinimas negali būti trumpesnis, nei 4 simboliai');}
    if (nameErrorMesages.length == 0) {
      var partyName = {name: this.state.name.trim()};
      axios
      .post('/party', partyName)
      .then(function(response){
        self.context.router.push('/admin/party');
      })
      .catch(function(err){
        console.error('PartyCreateContainer.onHandleSubmit.axios', err);
        self.setState({nameClone : true});
      });
    } else {
      this.setState({nameErrorMesages : nameErrorMesages});
    }
  },
  render: function() {
    return (
      <PartyCreateEditFormComponent
       onHandleNameChange={this.onHandleNameChange}
       name={this.state.name}
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
