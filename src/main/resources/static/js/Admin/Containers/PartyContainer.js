function getParties(self) {
  axios
  .get('/party')
  .then(function(response){
    self.setState({
      partyList :  response.data,
      isLoading : false,
    });
  })
  .catch(function(err){
    console.error('PartyContainer.componentWillMount.axios.get.party', err);
  });
}

var PartyContainer = React.createClass({
  getInitialState: function() {
    return {
      partyList : [],
      isLoading : true,
      succesCreateText : '',
    };
  },
  onHandleDelete : function(id){
    var self = this;
    axios
      .delete('/party/'+id)
      .then(function(response){
        getParties(self);
      })
      .catch(function(err){
        console.error('PartyContainer.onHandleDelete.axios.', err);
      });
  },
  componentWillMount: function() {
    console.log(this.props);
    if (this.props.location.query.succesCreateText != null) {
      this.setState({succesCreateText : this.props.location.query.succesCreateText});
    }
    getParties(this);
  },
  render: function() {
    if (this.state.isLoading) {
      return (
        <div>
          <img src='./Images/loading.gif'/>
        </div>
      );
    } else {
      console.log(this.state.partyList);
      return (
        <PartyListViewComponent
          partyList={this.state.partyList}
          succesCreateText={this.state.succesCreateText}
          onHandleDelete={this.onHandleDelete}
        />
      );
    }
  }
});

window.PartyContainer = PartyContainer;
