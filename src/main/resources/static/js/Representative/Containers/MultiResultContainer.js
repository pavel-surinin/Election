var MultiResultContainer = React.createClass({
  getInitialState: function() {
    return {
      partyList: [],
    };
  },
  componentWillMount: function() {
    var self = this;
    axios
      .get('/party')
      .then(function(response){
        self.setState({
          partyList :  response.data,
        });
      })
      .catch(function(err){
        console.error('MultiResultContainer.componentWillMount.axios.get.party', err);
      });
  },
  render: function() {
    return (
      <MultiResultComponent
        list = {this.state.partyList}
      />
    );
  }

});

window.MultiResultContainer = MultiResultContainer;
